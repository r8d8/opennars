package nars.nar.experimental;

import nars.Global;
import nars.NAR;
import nars.bag.BagBudget;
import nars.concept.Concept;
import nars.nal.PremiseMatch;
import nars.nar.Default;
import nars.process.ConceptProcess;
import nars.task.Task;
import nars.term.Termed;
import nars.term.Terms;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * particle that travels through the graph,
 * responsible for deciding what to derive
 */
public class Derivelet {


    /**
     * modulating the TTL (time-to-live) allows the system to control
     * the quality of attention it experiences.
     * a longer TTL will cause derivelets to restart
     * less frequently and continue exploring potentially "yarny"
     * paths of knowledge
     */
    int ttl;


    /**
     * current location
     */
    public BagBudget<Concept> concept;

    /**
     * utility context
     */
    public DeriveletContext context;

    PremiseMatch matcher;

    /**
     * temporary re-usable array for batch firing
     */
    private final Set<BagBudget<Termed>> terms = Global.newHashSet(1);
    /**
     * temporary re-usable array for batch firing
     */
    private final Set<BagBudget<Task>> tasks = Global.newHashSet(1);

    private BagBudget[] termsArray = new BagBudget[0];
    private BagBudget[] tasksArray = new BagBudget[0];

    public static int firePremises(BagBudget<Concept> conceptLink, BagBudget<Task>[] tasks, BagBudget<Termed>[] terms, Consumer<ConceptProcess> proc, NAR nar) {

        int total = 0;

        for (BagBudget<Task> taskLink : tasks) {
            if (taskLink == null) break;

            for (BagBudget<Termed> termLink : terms) {
                if (termLink == null) break;

                if (Terms.equalSubTermsInRespectToImageAndProduct(taskLink.get().term(), termLink.get().term()))
                    continue;

                total+= ConceptProcess.fireAll(
                    nar, conceptLink, taskLink, termLink, proc);
            }
        }

        return total;
    }


    /**
     * iteratively supplies a matrix of premises from the next N tasklinks and M termlinks
     * (recycles buffers, non-thread safe, one thread use this at a time)
     */
    public int firePremiseSquare(
            NAR nar,
            Consumer<ConceptProcess> proc,
            BagBudget<Concept> conceptLink,
            int tasklinks, int termlinks, Predicate<BagBudget> each) {

        Concept concept = conceptLink.get();

        concept.getTaskLinks().sample(tasklinks, each, tasks).commit();
        if (tasks.isEmpty()) return 0;

        concept.getTermLinks().sample(termlinks, each, terms).commit();
        if (terms.isEmpty()) return 0;

        //convert to array for fast for-within-for iterations
        tasksArray = this.tasks.toArray(tasksArray);
        this.tasks.clear();

        termsArray = this.terms.toArray(termsArray);
        this.terms.clear();

        return firePremises(conceptLink,
                tasksArray, termsArray,
                proc, nar);

    }


    private NAR nar() {
        return context.nar;
    }

    /**
     * determines a next concept to move adjacent to
     * the concept it is currently at
     */
    public Concept nextConcept() {

        final BagBudget<Concept> concept = this.concept;

        if (concept == null) {
            return null;
        }

        final float x = context.nextFloat();
        Concept c = concept.get();

        //calculate probability it will stay at this concept
        final float stayProb = 0.5f;//(concept.getPriority()) * 0.5f;
        if (x < stayProb) {
            //stay here
            return c;
        } else {
            float rem = 1.0f - stayProb;


            final BagBudget tl = ((x > (stayProb + (rem / 2))) ?
                    c.getTermLinks() :
                    c.getTaskLinks())
                    .sample();

            if (tl != null) {
                c = context.concept(((Termed) tl.get()));
                if (c != null) return c;
            }
        }

        return null;
    }

    /**
     * run next iteration; true if still alive by end, false if died and needs recycled
     */
    public final boolean cycle(final long now) {

        if (this.ttl-- == 0) {
            //died
            return false;
        }

        //TODO dont instantiate BagBudget
        if ((this.concept = new BagBudget(nextConcept(), 0, 0, 0)) == null) {
            //dead-end
            return false;
        }


        int tasklinks = 1;
        int termlinks = 2;

        int fired = firePremiseSquare(context.nar,
                perPremise, this.concept,
                tasklinks, termlinks,
                Default.simpleForgetDecay
        );

        return fired > 0;
    }

    final Consumer<Task> perDerivation = (derived) -> {
        final NAR n = nar();

        derived = n.validInput(derived);
        if (derived != null)
            n.process(derived);
    };

    final Consumer<ConceptProcess> perPremise = p ->
            DeriveletContext.deriver.run(p, matcher, perDerivation);


    public final void start(final Concept concept, int ttl, final DeriveletContext context) {
        this.context = context;
        this.concept = new BagBudget(concept, 0, 0, 0); //TODO
        this.ttl = ttl;
        this.matcher = new PremiseMatch(context.rng);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '@' + concept;
    }


}
