package nars.nal.stamp;

import nars.Memory;
import nars.budget.DirectBudget;
import nars.io.JSONOutput;
import nars.nal.Sentence;
import nars.nal.Task;
import nars.nal.nal7.Tense;
import nars.nal.nal8.Operation;
import nars.nal.term.Compound;

/**
 * applies Stamp information to a sentence. default IStamp implementation.
 */
public class Stamper<C extends Compound> extends DirectBudget implements Stamp, StampEvidence, AbstractStamper {

    protected long[] evidentialBase = null;

    protected int duration;
    protected long creationTime = Stamp.UNPERCEIVED;

    protected long occurrenceTime;

//    /**
//     * used when the occurrence time cannot be estimated, means "unknown"
//     */
    //public static final long UNKNOWN = Integer.MAX_VALUE;


    protected Stamp a = null;

    protected Stamp b = null;

    private long[] evidentialSetCached;

    @Deprecated public Stamper(final Memory memory, final Tense tense) {
        this(memory, memory.time(), tense);
    }

    @Deprecated public Stamper(final Memory memory, long creationTime, final Tense tense) {
        this(memory, creationTime, Stamp.getOccurrenceTime(creationTime, tense, memory.duration()));
    }

    public Stamper(Operation operation, Memory memory, Tense tense) {
        this(operation.getTask().sentence, memory, tense);
    }
    public Stamper(Sentence s, Memory memory, Tense tense) {
        this(s, s.getCreationTime(), Stamp.getOccurrenceTime(s.getCreationTime(), tense, memory.duration()));
    }

    public Stamper(final Memory memory, long creationTime, final long occurenceTime) {
        this.setDuration(memory.duration());
        this.setCreationTime(creationTime);
        this.setOccurrenceTime(occurenceTime);
    }

    public Stamper(long[] evidentialBase, Stamp a, Stamp b, long creationTime, long occurrenceTime, int duration) {
        this.setA(a);
        this.setB(b);
        this.setCreationTime(creationTime);
        this.setOccurrenceTime(occurrenceTime);
        this.setDuration(duration);
        this.setEvidentialBase(evidentialBase);
    }

    public Stamper(Memory memory, long occurrence) {
        this(memory, memory.time(), occurrence);
    }

    public Stamper(Stamp s, long occ) {
        this(s, s.getOccurrenceTime(), occ);
    }
    public Stamper(Task task, long occ) {
        this(task.sentence, occ);
    }

    public Stamper() {
        super();
    }

    public Stamper clone() {
        return new Stamper(getEvidentialBase(), getA(), getB(), getCreationTime(), getOccurrenceTime(), getDuration());
    }

    public Stamper cloneEternal() {
        return new Stamper(getEvidentialBase(), getA(), getB(), getCreationTime(), Stamp.ETERNAL, getDuration());
    }

    public Stamper(long[] evidentialBase, long creationTime, long occurrenceTime, int duration) {
        this(evidentialBase, null, null, creationTime, occurrenceTime, duration);
    }

    public Stamper(Stamp a, long creationTime, long occurrenceTime) {
        this(a, null, creationTime, occurrenceTime);
    }

    public Stamper(Stamp a, Stamp b, long creationTime, long occurrenceTime) {
        this.setA(a);
        this.setB(b);
        this.setCreationTime(creationTime);
        this.setOccurrenceTime(occurrenceTime);
    }

    @Deprecated
    public Stamper<C> setOccurrenceTime(long occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
        return this;
    }

    @Override
    public Stamp setDuration(int d) {
        this.duration = d;
        return this;
    }

    @Override
    public Stamp setEvidentialBase(long[] b) {
        this.evidentialBase = b;
        return this;
    }


    public Stamper<C> setCreationTime(long creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public Stamper<C> setEternal() {
        return setOccurrenceTime(Stamp.ETERNAL);
    }



    @Override public void applyToStamp(final Stamp target) {

        if (target instanceof Stamper) {
            //HACK special handling when target is Stamper: include A and B reference
            Stamper s = (Stamper)target;
            s.setA(getA());
            s.setB(getB());
        }

        target.setDuration(getDuration())
              .setTime(getCreationTime(), getOccurrenceTime())
              .setEvidence(getEvidentialBase(), getEvidentialSetCached());

    }

    /**
     * creation time of the stamp
     */
    @Override
    public long getCreationTime() {
        return  creationTime;
    }

    /**
     * duration (in cycles) which any contained intervals are measured by
     */
    public int getDuration() {
        if (this.duration == 0) {
            if (getB() !=null)
                return (this.duration = getB().getDuration());
            else if (getA() !=null)
                return (this.duration = getA().getDuration());
            else
                return -1;
        }
        return duration;
    }

    /**
     * estimated occurrence time of the event*
     */
    @Override
    public long getOccurrenceTime() {
        return occurrenceTime;
    }

    @Override
    public Stamp cloneWithNewCreationTime(long newCreationTime) {
        throw new UnsupportedOperationException("Not impl");
    }

    @Override
    public Stamp cloneWithNewOccurrenceTime(long newOcurrenceTime) {
        throw new UnsupportedOperationException("Not impl");
    }

    @Override
    public long[] getEvidentialSet() {
        updateEvidence();
        long[] es = getEvidentialSetCached();
        if (es == null) {
            this.evidentialSetCached = es = Stamp.toSetArray(getEvidentialBase());
        }
        return es;
    }

    /**
     * serial numbers. not to be modified after Stamp constructor has initialized it
     */
    public long[] getEvidentialBase() {
        updateEvidence();
        return evidentialBase;
    }

    protected void updateEvidence() {
        if (evidentialBase == null) {

            if ((getA() == null) && (getB() == null)) {
                //supplying no evidence will be assigned a new serial
                //but this should only happen for input tasks (with no parent)
            } else if (isDouble()) {
                this.evidentialBase = (Stamp.zip(getA().getEvidentialBase(), getB().getEvidentialBase()));
            }
            else {
                //Single premise

                Stamp p = null; //parent to inherit some properties from
                if (getA() == null) p = getB();
                else if (getB() == null) p = getA();

                if (p!=null) {
                    this.evidentialBase = (p.getEvidentialBase());
                    this.evidentialSetCached = (p.getEvidentialSet());
                }
            }

        }
    }


    @Override
    public String toString() {
        try {
            return JSONOutput.stringFromFields(this);
        }
        catch (StackOverflowError e) {
            e.printStackTrace();
            //TODO prevent this
            return getClass().getSimpleName() + "[JSON_Error]";
        }
    }

    public boolean isEternal() {
        return getOccurrenceTime() == Stamp.ETERNAL;
    }


    /**
     * optional first parent stamp
     */
    public Stamp getA() {
        return a;
    }

    public Stamp setA(Stamp a) {
        if (a == this)
            throw new RuntimeException("Circular parent stamp");
        this.a = a;
        return this;
    }

    /**
     * optional second parent stamp
     */
    public Stamp getB() {
        return b;
    }

    public Stamp setB(Stamp b) {
        if (b == this)
            throw new RuntimeException("Circular parent stamp");
        this.b = b; return this;
    }

    public long[] getEvidentialSetCached() {
        return evidentialSetCached;
    }


    public boolean isDouble() {
        return this.a!=null & this.b!=null;
    }
}
