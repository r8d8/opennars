package nars.operator.io;


import nars.core.Memory;
import nars.logic.entity.Task;
import nars.logic.entity.Term;
import nars.logic.nal8.Operation;
import nars.logic.nal8.Operator;

import java.util.List;

/** sets the current SELF term */
public class Self extends Operator {

    public Self() {
        super("^self");
    }

    @Override
    protected List<Task> execute(Operation operation, Term[] args, Memory memory) {
        memory.setSelf(args[0]);
        return null;
    }

    @Override
    public boolean isImmediate() {
        return true;
    }
}