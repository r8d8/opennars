package nars.util.data;

import java.util.Random;

//Extremely fast, medium quality randomness: http://www.javamex.com/tutorials/random_numbers/java_util_random_subclassing.shtml
public class XORShiftRandom extends Random {
    
    public static final XORShiftRandom global = new XORShiftRandom();


    private long seed = System.nanoTime();

    public XORShiftRandom() {
        super();
        this.seed = System.nanoTime();
    }

    public XORShiftRandom(long seed) {
        super();
        this.seed = seed;
    }

    @Override
    protected int next(final int nbits) {
        // N.B. Not thread-safe!
        long x = this.seed;
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        this.seed = x;
        x &= ((1L << nbits) - 1);
        return (int) x;
    }

    @Override
    public synchronized void setSeed(long newSeed) {
        this.seed = newSeed;
    }

}
