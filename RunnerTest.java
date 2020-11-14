package Project3;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import student.TestCase;

/**
 * test class
 * 
 * @author honghaozhang073 and adam chen
 * @version 10/24/2020
 */
public class RunnerTest extends TestCase {
    static private Random value = new Random();

    /**
     * setup
     */
    public void setUp() {
        // args = new String[2];

    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMain() throws IOException {
        String[] args1 = { "Sample32.bin", "15" };

        int val;
        float val2;
        assert (args1.length == 2) : "\nUsage:" + " Genfile_proj3_2020"
            + " <filename> <size>"
            + "\nOptions \nSize is measured in blocks of 8192 bytes";

        int filesize = Integer.parseInt(args1[1]); // Size of file in blocks
        DataOutputStream file = new DataOutputStream(new BufferedOutputStream(
            new FileOutputStream(args1[0])));

        for (int i = 0; i < filesize; i++) {
            for (int j = 0; j < 1024; j++) {
                val = (int)(value.nextInt(Integer.MAX_VALUE));
                file.writeInt(val);
                val2 = (float)(value.nextFloat() * Float.MAX_VALUE);
                file.writeFloat(val2);
            }
        }

        file.flush();
        file.close();
        new Externalsorting();
        String[] args2 = { "Sample32.bin", "file.bin" };
        Externalsorting.main(args2);
        String output = systemOut().getHistory();
        assertNotNull(output);
        File temp = new File("Sampel32.bin");
        File temp2 = new File("file.bin");
        temp.delete();
        temp2.delete();
    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMai1() throws IOException {
        String[] args1 = { "Sample32.bin", "16" };

        int val;
        float val2;
        assert (args1.length == 2) : "\nUsage: " + "Genfile_proj3_2020"
            + " <filename> <size>"
            + "\nOptions \nSize is measured in blocks of 8192 bytes";

        int filesize = Integer.parseInt(args1[1]); // Size of file in blocks
        DataOutputStream file = new DataOutputStream(new BufferedOutputStream(
            new FileOutputStream(args1[0])));

        for (int i = 0; i < filesize; i++) {
            for (int j = 0; j < 1024; j++) {
                val = (int)(value.nextInt(Integer.MAX_VALUE));
                file.writeInt(val);
                val2 = (float)(value.nextFloat() * Float.MAX_VALUE);
                file.writeFloat(val2);
            }
        }

        file.flush();
        file.close();
        new Externalsorting();
        String[] args2 = { "Sample32.bin", "file.bin" };
        Externalsorting.main(args2);
        String output = systemOut().getHistory();
        assertNotNull(output);
        File temp = new File("Sampel32.bin");
        File temp2 = new File("file.bin");
        temp.delete();
        temp2.delete();
    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMai2() throws IOException {
        String[] args1 = { "Sample32.bin", "32" };

        int val;
        float val2;
        assert (args1.length == 2) : "\nUsage:"
            + " Genfile_proj3_2020 <filename> <size>"
            + "\nOptions \nSize is measured in blocks of 8192 bytes";

        int filesize = Integer.parseInt(args1[1]); // Size of file in blocks
        DataOutputStream file = new DataOutputStream(new BufferedOutputStream(
            new FileOutputStream(args1[0])));

        for (int i = 0; i < filesize; i++) {
            for (int j = 0; j < 1024; j++) {
                val = (int)(value.nextInt(Integer.MAX_VALUE));
                file.writeInt(val);
                val2 = (float)(value.nextFloat() * Float.MAX_VALUE);
                file.writeFloat(val2);
            }
        }
        file.flush();
        file.close();
        new Externalsorting();
        String[] args2 = { "Sample32.bin", "file.bin" };
        Externalsorting.main(args2);
        String output = systemOut().getHistory();
        assertNotNull(output);
        File temp = new File("Sampel32.bin");
        File temp2 = new File("file.bin");
        temp.delete();
        temp2.delete();
    }


    /**
     * test main 4
     * 
     * @throws IOException
     *             exception
     */
    public void testMai4() throws IOException {
        String[] args1 = { "Sample32.bin", "128" };

        int val;
        float val2;
        assert (args1.length == 2) : "\nUsage:"
            + " Genfile_proj3_2020 <filename> <size>"
            + "\nOptions \nSize is measured in blocks of 8192 bytes";

        int filesize = Integer.parseInt(args1[1]); // Size of file in blocks
        DataOutputStream file = new DataOutputStream(new BufferedOutputStream(
            new FileOutputStream(args1[0])));

        for (int i = 0; i < filesize; i++) {
            for (int j = 0; j < 1024; j++) {
                val = (int)(value.nextInt(Integer.MAX_VALUE));
                file.writeInt(val);
                val2 = (float)(value.nextFloat() * Float.MAX_VALUE);
                file.writeFloat(val2);
            }
        }
        file.flush();
        file.close();
        new Externalsorting();
        String[] args2 = { "Sample32.bin", "file.bin" };
        Externalsorting.main(args2);
        String output = systemOut().getHistory();
        assertNotNull(output);
        File temp = new File("Sampel32.bin");
        File temp2 = new File("file.bin");
        temp.delete();
        temp2.delete();
    }
    /**
     * test main 4
     * 
     * @throws IOException
     *             exception
     */
    public void testMai5() throws IOException {
        String[] args1 = { "Sample32.bin", "2000" };

        int val;
        float val2;
        assert (args1.length == 2) : "\nUsage:"
            + " Genfile_proj3_2020 <filename> <size>"
            + "\nOptions \nSize is measured in blocks of 8192 bytes";

        int filesize = Integer.parseInt(args1[1]); // Size of file in blocks
        DataOutputStream file = new DataOutputStream(new BufferedOutputStream(
            new FileOutputStream(args1[0])));

        for (int i = 0; i < filesize; i++) {
            for (int j = 0; j < 1024; j++) {
                val = (int)(value.nextInt(Integer.MAX_VALUE));
                file.writeInt(val);
                val2 = (float)(value.nextFloat() * Float.MAX_VALUE);
                file.writeFloat(val2);
            }
        }
        file.flush();
        file.close();
        new Externalsorting();
        String[] args2 = { "Sample32.bin", "file.bin" };
        Externalsorting.main(args2);
        String output = systemOut().getHistory();
        assertNotNull(output);
        File temp = new File("Sampel32.bin");
        File temp2 = new File("file.bin");
        temp.delete();
        temp2.delete();
    }
}
