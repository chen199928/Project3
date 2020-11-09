package Project3;





import java.io.IOException;

import student.TestCase;

/**
 * test class
 * 
 * @author honghaozhang073 and adam chen
 * @version 10/24/2020
 */
public class RunnerTest extends TestCase {
    private String[] args;

    /**
     * setup
     */
    public void setUp() {
        args = new String[2];

    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMain() throws IOException {

        args[0] = "Sampledata_16blocks.bin";
        args[1] = "filenames.bin";
        new Externalsorting();
        Externalsorting.main(args);
        String output = systemOut().getHistory();
        assertNotNull(output);
    }
    
    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMain2() throws IOException {

        args[0] = "Sampledata__32blocks.bin";
        args[1] = "filenamess.bin";
        new Externalsorting();
        Externalsorting.main(args);
        String output = systemOut().getHistory();
        assertNotNull(output);
    }
}