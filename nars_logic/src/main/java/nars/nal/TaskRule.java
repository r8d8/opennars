package nars.nal;

import com.google.common.collect.Sets;
import nars.Global;
import nars.Op;
import nars.nal.meta.PostCondition;
import nars.nal.meta.PreCondition;
import nars.nal.meta.TaskBeliefPair;
import nars.nal.meta.match.Ellipsis;
import nars.nal.meta.op.Derive;
import nars.nal.meta.op.PostSolve;
import nars.nal.meta.op.Solve;
import nars.nal.meta.post.*;
import nars.nal.meta.pre.*;
import nars.nal.nal1.Inheritance;
import nars.nal.nal4.Product;
import nars.nal.nal4.ProductN;
import nars.term.Term;
import nars.term.Terms;
import nars.term.atom.Atom;
import nars.term.compile.TermIndex;
import nars.term.compound.Compound;
import nars.term.transform.CompoundTransform;
import nars.term.transform.VariableNormalization;
import nars.term.variable.Variable;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A rule which produces a Task
 * contains: preconditions, predicates, postconditions, post-evaluations and metainfo
 */
public class TaskRule extends ProductN implements Level {



    public boolean immediate_eternalize = false;


    public boolean anticipate = false;
    public boolean sequenceIntervalsFromTask = false;
    public boolean sequenceIntervalsFromBelief = false;

    /** conditions which can be tested before term matching */
    public PreCondition[] prePreconditions;

    /** conditions which are tested after term matching, including term matching itself */
    public PreCondition[] postPreconditions;

    public PostCondition[] postconditions;

    public TaskBeliefPair pattern;

    //it has certain pre-conditions, all given as predicates after the two input premises


    boolean allowBackward = false;

    /** maximum of the minimum NAL levels involved in the postconditions of this rule */
    public int minNAL;

    private String str;
    protected String source;

    public final ProductN getPremise() {
        return (ProductN) term(0);
    }

    public final ProductN getConclusion() {
        return (ProductN) term(1);
    }

    public TaskRule(Product premises, Product result) {
        super(premises, result);
        this.str = super.toString();
    }


//    public final boolean validTaskPunctuation(final char p) {
//        if ((p == Symbols.QUESTION) && !allowQuestionTask)
//            return false;
//        return true;
//    }

    protected final void ensureValid() {

        if (getConclusionTerm().containsTemporal()) {
            if ((!getTaskTermPattern().containsTemporal())
                    &&
                    (!getBeliefTermPattern().containsTemporal())) {
                //if conclusion is temporal term but the premise has none:

                String s = toString();
                if ((!s.contains("after")) && (!s.contains("concurrent") && (!s.contains("measure")))) {
                    //System.err.println
                  throw new RuntimeException
                            ("Possibly invalid temporal rule from atemporal premise: " + this);

                }
            }
        }


        if (postconditions.length == 0)
            throw new RuntimeException(this + " has no postconditions");
        if (!Variable.hasPatternVariable(getTask()))
            throw new RuntimeException("rule's task term pattern has no pattern variable");
        if (!Variable.hasPatternVariable(getBelief()))
            throw new RuntimeException("rule's task belief pattern has no pattern variable");
        if (!Variable.hasPatternVariable(getConclusionTerm()))
            throw new RuntimeException("rule's conclusion belief pattern has no pattern variable");
    }


    @Override
    public final TaskRule normalized() {
        return (TaskRule) this.transform(uppercaseAtomsToPatternVariables);
    }

//    @Override
//    public Term index(TermIndex termIndex) {
//
//        //task and belief pattern term
//        for (int i = 0; i < 2; i++) {
//            Term s = (Term) getPremise().terms()[i];
//            if (s == null)
//                throw new RuntimeException("null premise term: " + this);
//            getPremise().terms()[i] = s;
//        }
//
//        //conclusion pattern term
//        getConclusion().terms()[0] = (Term) getConclusion().terms()[0];
//
//        return this;
//    }


    //    public Product result() {
//        return (Product) term(1);
//    }



    /** add the sequence of involved conditions to a list, for one given postcondition (ex: called for each this.postconditions)  */
    public List<PreCondition> getConditions(PostCondition post) {

        int n = prePreconditions.length + postPreconditions.length;

        List<PreCondition> l = Global.newArrayList(n*2);

        ///--------------
        for (PreCondition p : prePreconditions)
            p.addConditions(l);

        l.add(new Solve.Truth(post.truth, post.desire, post.puncOverride));

        ///--------------
        //l.add(new RuleMatch.Stage(RuleMatch.MatchStage.Pattern));

        for (PreCondition p : postPreconditions)
            p.addConditions(l);

        ///--------------




        if (post.afterConclusions.length > 0) {
            l.add(new Solve(post.term, this, true ));
            Collections.addAll(l, post.afterConclusions);
            l.add(PostSolve.the);
        }
        else {
            l.add(new Solve(post.term, this, false ));
        }

        l.add(new Derive(this, anticipate, immediate_eternalize));

        return l;
    }


    /**
     * non-null;
     * if it returns Op.VAR_PATTERN this means that any type can apply
     */
    public final Op getTaskTermType() {
        return getTask().op();
    }

    public void setSource(String source) {
        this.source = source;
    }

    /** source string that generated this rule (for debugging) */
    public String getSource() {
        return source;
    }

    protected final Term getTask() {
        return getPremise().term(0);
    }


    /**
     * returns Op.NONE if there is no belief term type;
     * if it returns Op.VAR_PATTERN this means that any type can apply
     */
    public final Op getBeliefTermType() {
        return getBelief().op();
    }

    protected final Term getBelief() {
        return getPremise().term(1);
    }

    protected final Term getConclusionTerm() {
        return getConclusion().term(0);
    }


//    /**
//     * test applicability of this rule with a specific maximum NAL level
//     */
//    public boolean levelValid(final int nalLevel) {
//        return Terms.levelValid(getTask(), nalLevel) &&
//                Terms.levelValid(getBelief(), nalLevel) &&
//                Terms.levelValid(getResult(), nalLevel);
//    }

//    public boolean isReversible() {
//        //TEST
//        if (toString().contains("shift_occurrence"))
//            return false;
//        if (toString().contains("substitute"))
//            return false;
//        return true;
//    }
//

//    /** how many unique pattern variables are present */
//    public int numPatternVariables() {
//        return numPatternVar;
//    }



//    public String getFlagsString() {
//        return "TaskRule{" +
//                "  immediate_eternalize=" + immediate_eternalize +
//                ", anticipate=" + anticipate +
//                ", sequenceIntervalsFromTask=" + sequenceIntervalsFromTask +
//                ", sequenceIntervalsFromBelief=" + sequenceIntervalsFromBelief +
//                ", allowQuestionTask=" + allowQuestionTask +
//                ", allowBackward=" + allowBackward +
//                '}';
//    }

    @Override
    public final String toString() {
        return str;
    }

    public final Term task() {
        return pattern.term(0);
    }
    public final Term belief() {
        return pattern.term(1);
    }

    /** deduplicate and generate match-optimized compounds for rules */
    public void compile(TermIndex patterns) {
        Term[] premisePattern = ((Product) term(0)).terms();
        Term taskPattern = premisePattern[0];
        Term beliefPattern = premisePattern[1];
        premisePattern[0] = compilePattern(taskPattern, patterns);
        premisePattern[1] = compilePattern(beliefPattern, patterns);
    }

    static Term compilePattern(Term c, TermIndex patterns) {
        Term x = patterns.getTerm(c);
        if (Global.DEBUG) {
            if (x == null || !x.equals(c))
                throw new RuntimeException("index fault");
        }
        return x;
    }


    final static class UppercaseAtomsToPatternVariables implements CompoundTransform<Compound, Term> {


        @Override
        public final boolean test(Term term) {
            if (term instanceof Atom) {
                String name = term.toString();
                return (Character.isUpperCase(name.charAt(0)));
            }
            return false;
        }

        @Override
        public Term apply(Compound containingCompound, Term v, int depth) {

            //do not alter postconditions
            if ((containingCompound instanceof Inheritance)
                    && PostCondition.reservedMetaInfoCategories.contains(
                    ((Inheritance) containingCompound).getPredicate()))
                return v;

            return Variable.the(Op.VAR_PATTERN, v.bytes());
        }
    }

    final static UppercaseAtomsToPatternVariables uppercaseAtomsToPatternVariables = new UppercaseAtomsToPatternVariables();



    public final TaskRule normalizeRule() {
        TaskRule tr = (TaskRule) new TaskRuleVariableNormalization(this).get();
        if (tr == null)
            return null;
        return tr.setup();
    }


    @Override
    public final TaskRule clone(Term[] replaced) {
        return new TaskRule((Product) replaced[0], (Product) replaced[1]);
    }

    public final TaskRule setup() {


        //1. construct precondition term array
        //Term[] terms = terms();

        Term[] precon = ((Product) term(0)).terms();
        Term[] postcons = ((Product) term(1)).terms();


        List<PreCondition> prePreConditionsList = Global.newArrayList(precon.length);
        List<PreCondition> preConditionsList = Global.newArrayList(precon.length);


        List<PreCondition> afterConcs = Global.newArrayList(0);


        Term taskTermPattern = getTaskTermPattern();
        Term beliefTermPattern = getBeliefTermPattern();

        if (beliefTermPattern.hasAny(Op.ATOM)) {
            throw new RuntimeException("belief term must be a pattern: " + beliefTermPattern);
        }

        //if it contains an atom term, this means it is a modifier,
        //and not a belief term pattern
        //(which will not reference any particular atoms)


        this.pattern = new TaskBeliefPair(taskTermPattern, beliefTermPattern);

        final MatchTaskBelief matcher = new MatchTaskBelief(pattern);
        preConditionsList.add(matcher);


        //additional modifiers: either preConditionsList or beforeConcs, classify them here
        for (int i = 2; i < precon.length; i++) {
//            if (!(precon[i] instanceof Inheritance)) {
//                System.err.println("unknown precondition type: " + precon[i] + " in rule: " + this);
//                continue;
//            }

            Inheritance predicate = (Inheritance) precon[i];
            Term predicate_name = predicate.getPredicate();

            final String predicateNameStr = predicate_name.toString().substring(1);//.replace("^", "");

            PreCondition next = null, preNext = null;

            final Term[] args;
            final Term arg1, arg2;

            //if (predicate.getSubject() instanceof SetExt) {
                //decode precondition predicate arguments
            args = ((Product)(predicate.getSubject())).terms();
            arg1 = args[0];
            arg2 = (args.length > 1) ? args[1] : null;
            /*} else {
                throw new RuntimeException("invalid arguments");*/
                /*args = null;
                arg1 = arg2 = null;*/
            //}

            switch (predicateNameStr) {

                case "equal":
                    next = Equal.make(arg1, arg2);
                    break;

                case "input_premises":
                    next = new InputPremises(arg1, arg2);
                    break;

                case "not_equal":
                    next = NotEqual.make(arg1, arg2);
                    break;

                case "set_ext":
                    next = new ExtSet(arg1);
                    break;
                case "set_int":
                    next = new IntSet(arg1);
                    break;
                case "not_set":
                    next = new NotSet(arg1);
                    break;
                case "not_conjunction":
                    next = new NotConjunction(arg1);
                    break;
                case "not_implication_or_equivalence":
                    next = new NotImplOrEquiv(arg1);
                    break;
                case "no_common_subterm":
                    next = NoCommonSubterm.make(arg1, arg2);
                    break;




                case "event":
                    preNext = Temporality.both;
                    break;

                case "temporal":
                    preNext = Temporality.either;
                    break;

                case "after":
                    preNext = After.the;
                    break;

                case "concurrent":
                    preNext = Concurrent.the;
                    break;

                case "shift_occurrence_forward":
                    next = ShiftOccurrence.make(arg1, arg2, true);
                    break;
                case "shift_occurrence_backward":
                    next = ShiftOccurrence.make(arg1, arg2, false);
                    break;

                case "measure_time":
                    if (args.length!=1)
                        throw new RuntimeException("measure_time requires 1 component");

                    preNext = Temporality.both;
                    next = new MeasureTime(arg1);
                    break;



                case "substitute":
                    afterConcs.add(new Substitute(arg1, (Variable)arg2));
                    break;

                case "substitute_if_unifies":
                    afterConcs.add(new SubstituteIfUnified(arg1, arg2, args[2]));
                    break;

                case "intersection":
                    afterConcs.add(new Intersect(arg1, arg2, args[2]));
                    break;

                case "union":
                    afterConcs.add(new Unite(arg1, arg2, args[2]));
                    break;

                case "difference":
                    afterConcs.add(new Differ(arg1, arg2, args[2]));
                    break;

                case "task":
                    switch (arg1.toString()) {
                        case "negative":
                            preNext = new TaskNegative();
                            break;
                        case "\"?\"":
                            preNext = TaskPunctuation.TaskQuestion;
                            break;
                        case "\".\"":
                            preNext = TaskPunctuation.TaskJudgment;
                            break;
                        case "\"!\"":
                            preNext = TaskPunctuation.TaskGoal;
                            break;
                        default:
                            throw new RuntimeException("Unknown task punctuation type: " + predicate.getSubject());
                    }
                    break;

                default:
                    throw new RuntimeException("unhandled postcondition: " + predicateNameStr + " in " + this + "");

            }


            if (preNext!=null) {
                if (!prePreConditionsList.contains(preNext)) //unique
                    prePreConditionsList.add(preNext);
            }
            if (next != null)
                preConditionsList.add(next);
        }

        //store to arrays
        this.prePreconditions = prePreConditionsList.toArray(new PreCondition[prePreConditionsList.size()]);
        this.postPreconditions = preConditionsList.toArray(new PreCondition[preConditionsList.size()]);


        List<PostCondition> postConditions = Global.newArrayList();

        for (int i = 0; i < postcons.length; ) {
            Term t = postcons[i++];
            if (i >= postcons.length)
                throw new RuntimeException("invalid rule: missing meta term for postcondition involving " + t);


            Term[] modifiers = ((Product) postcons[i++]).terms();

            PreCondition[] afterConclusions = afterConcs.toArray(new PreCondition[afterConcs.size()]);

            PostCondition pc = PostCondition.make(this, t,
                    afterConclusions,
                    Terms.toSortedSetArray(modifiers));

            if (pc!=null)
                postConditions.add( pc );
        }

        if (Sets.newHashSet(postConditions).size()!=postConditions.size())
            throw new RuntimeException("postcondition duplicates:\n\t" + postConditions);

        this.postconditions = postConditions.toArray( new PostCondition[postConditions.size() ] );


        //TODO add modifiers to affect minNAL (ex: anything temporal set to 7)
        //this will be raised by conclusion postconditions of higher NAL level
        this.minNAL =
                Math.max(this.minNAL,
                    Math.max(
                            Terms.maxLevel(pattern.term(0)),
                            Terms.maxLevel(pattern.term(1)
                            )));

        ensureValid();

        return this;
    }

    public final Term getTaskTermPattern() {
        return ((Product) term(0)).terms()[0];
    }
    public final Term getBeliefTermPattern() {
        return ((Product) term(0)).terms()[1];
    }

    public final void setAllowBackward(boolean allowBackward) {
        this.allowBackward = allowBackward;
    }


    //    //TEMPORARY for testing, to make sure the postcondition equality guarantees rule equality
//    boolean deepEquals(Object obj) {
//        /*
//        the precondition uniqueness is guaranted because they exist as the terms of the rule meta-term which equality is already tested for
//         */
//        if (super.equals(obj)) {
//            if (!Arrays.equals(postconditions, ((TaskRule)obj).postconditions)) {
//                throw new RuntimeException(this + " and " + obj + " have equal Rule Product but inequal postconditions");
//            }
//
//            return true;
//        }
//        return false;
//    }


    /**
     * for each calculable "question reverse" rule,
     * supply to the consumer
     */
    public final void forEachQuestionReversal(BiConsumer<TaskRule,String> w) {

        //String s = w.toString();
        /*if(s.contains("task(\"?") || s.contains("task(\"@")) { //these are backward inference already
            return;
        }
        if(s.contains("substitute(")) { //these can't be reversed
            return;
        }*/

        if(!allowBackward) { //explicitely stated in the rules now
            return;
        }

        // T, B, [pre] |- C, [post] ||--

        Term T = this.getTask();
        Term B = this.getBelief();
        Term C = this.getConclusionTerm();

        //      C, B, [pre], task_is_question() |- T, [post]
        TaskRule clone1 = clone(C, B, T, true);
        w.accept(clone1, "C,B,[pre],question |- T,[post]");

        //      C, T, [pre], task_is_question() |- B, [post]
        TaskRule clone2 = clone(C, T, B, true);
        w.accept(clone2, "C,T,[pre],question |- B,[post]");

    }

    /**
     * for each calculable "question reverse" rule,
     * supply to the consumer
     */
    public final TaskRule forwardPermutation() {

        // T, B, [pre] |- C, [post] ||--

        Term T = this.getTask();
        Term B = this.getBelief();
        Term C = this.getConclusionTerm();

        //      B, T, [pre], task_is_question() |- T, [post]

        TaskRule clone1 = clone(B, T, C, false);
        return clone1;//.normalizeRule();
    }

    private final TaskRule clone(final Term newT, final Term newB, final Term newR, boolean question) {

        ProductN newPremise = null;
        if(question) {
            newPremise = (ProductN) Product.make(getPremise().termsCopy(TaskPunctuation.TaskQuestionTerm));
        } else {
            newPremise = (ProductN) Product.make(getPremise().terms());
        }

        newPremise.terms()[0] = newT;
        newPremise.terms()[1] = newB;

        final Product newConclusion = Product.make(getConclusion().cloneTermsReplacing(0, newR));

        TaskRule t = new TaskRule(newPremise, newConclusion);

        return t;
    }

//    /**
//     * -1 or +1 depending on how arg1 and arg2 match either Task/Belief of the premise
//     * @return +1 if first arg=task, second arg = belief, -1 if opposite,
//     * throws exception if incomplete match
//     */
//    public final int getTaskOrder(Term arg1, Term arg2) {
//
//        Product p = getPremises();
//        Term taskPattern = p.term(0);
//        Term beliefPattern = p.term(1);
//        if (arg2.equals(taskPattern) && arg1.equals(beliefPattern)) {
//            return -1;
//        } else if (arg1.equals(taskPattern) && arg2.equals(beliefPattern)) {
//            return 1;
//        } else {
//            throw new RuntimeException("after(X,Y) needs to match both taks and belief patterns, in one of 2 orderings");
//        }
//
//    }


    final public int nal() { return minNAL; }

    public static class TaskRuleVariableNormalization extends VariableNormalization {


        public TaskRuleVariableNormalization(Compound target) {
            super(target);
        }

        @Override protected Variable resolve(Variable v) {
            if (v instanceof Ellipsis) {
                return ((Ellipsis) v).target;
            }
            return v;
        }



        @Override protected Variable newVariable(final Variable v, final Variable alreadyNormalized, int serial) {

            if (alreadyNormalized!=null) {
                return alreadyNormalized;
            }

            Variable newVar = Variable.the(v.op(), serial);

            if (v instanceof Ellipsis) {
                Ellipsis e = (Ellipsis)v;
                return e.clone(newVar, this);
            }
            else {
                return newVar;
            }
        }

        @Override
        public final boolean testSuperTerm(Compound t) {
            //descend all, because VAR_PATTERN is not yet always considered a variable
            return true;
        }
    }
}




