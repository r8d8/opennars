package nars.nal.nal3;

import nars.Global;
import nars.nal.term.BaseCompound;
import nars.nal.term.DefaultCompound;
import nars.nal.term.Term;
import nars.util.data.sorted.AbstractSet;

import static nars.nal.term.Compound.verifySortedAndUnique;

/**
 * Base class for SetInt (intensional set) and SetExt (extensional set), where N>1
 */
abstract public class AbstractSetN extends DefaultCompound implements SetTensional {


    /**
     * Constructor with partial values, called by make
     * @param n The name of the term
     * @param arg The component list of the term
     */
    protected AbstractSetN(final Term[] arg) {
        super(arg);
        
        if (arg.length == 0)
            throw new RuntimeException("0-arg empty set");
        
        if (Global.DEBUG) { verifySortedAndUnique(arg, true); }
        
        init(arg);
    }
    
    

    abstract public BaseCompound clone();


}
