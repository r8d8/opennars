package nars.analyze;

import nars.core.NewNAR;
import org.junit.Ignore;

import java.io.FileNotFoundException;


@Ignore
public class NALysisDefault extends NALysis {

    public NALysisDefault(NewNAR b) {        super(b);    }

    public static void main(String[] args) throws FileNotFoundException {

        //csvOut = System.out;
        //dataOut = new FileOutputStream("/tmp/out.arff");

        nal1Default(1);
        nal2Default();
        nal3Default();
        nal5Default();

        //multistep issues:
        nal4Default();
        nal6Default();

        nal7Default();
        nal8Default();

        //results.printARFF(new PrintStream(dataOut));


    }
}

