

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
/**
 * On my honor: - I have not used source code obtained from another student, or
 * any other unauthorized source, either modified or unmodified.
 *
 * - All source code and documentation used in my program is either my original
 * work, or was derived by me from the source code published in the textbook for
 * this course.
 *
 * - I have not discussed coding details about this project with anyone other
 * than my partner (in the case of a joint submission), instructor, ACM/UPE
 * tutors or the TAs assigned to this course. I understand that I may discuss
 * the concepts of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes anything during
 * the discussion or modifies any computer file during the discussion. I have
 * violated neither the spirit nor letter of this restriction.
 * @author youweichen and honghao zhang
 * @version 11/13/2020
 * this is the external sorting class.
 */
public class Externalsorting {
/**
 * main method that implements external sorting
 * @param args argument 
 * @throws IOException exception
 */
    public static void main(String[] args) throws IOException {
        File inputFile = new File(args[0]);
        File random = new File(args[1]);
        File outputFile = File.createTempFile("temp", ".bin");
        //this get 32, 43, 512 1k correct
        //File outputFile = new File("temp.txt");
        //File result = new File(args[1]);
        FileWriter myWriter = new FileWriter(outputFile);
        //this get 32, 43, 512 1k correct
        
        //File out = new File(args[1]);
        File out = File.createTempFile("output", ".bin");
        RandomAccessFile read = new RandomAccessFile(inputFile, "r");
        int numBlocks = ((int)read.length()) / 8 / 1024;
        int numRun = ((int)read.length()) / 8 % 1024;
        RandomAccessFile write = new RandomAccessFile(outputFile, "rw");
        int countComma = 0;
        ArrayList<Integer> run = ReplacementSelection.replacementSelectionSort(
            read, write);

        // return a temp file
        File result = MergeSort.multiwayMerge(outputFile, out, run);
        FileInputStream instream = new FileInputStream(result);
        FileOutputStream outstream = new FileOutputStream(random);
        
        
        byte[] buffer = new byte[1024];
        
        int length;
        /*copying the contents from input stream to
         * output stream using read and write methods
         */
        while ((length = instream.read(buffer)) > 0){
            outstream.write(buffer, 0, length);
        }
        RandomAccessFile newBin = new RandomAccessFile(random, "r");

        if (numRun != 0) {
            numBlocks++;
        }
        for (int i = 0; i < numBlocks; i++) {
            int byte1 = i * 8192;
            newBin.seek(byte1);
            Record here = Record(newBin);
            if (here != null) {
                System.out.print(Integer.toString(here.getKey()) + " " + Float
                    .toString(here.getValue()));
                if ((i + 1) % 4 == 0) {
                    System.out.print("\n");
                }
                else {
                    if (i != numBlocks - 1) {
                        System.out.print(", ");
                    }
                }
            }
        }
        //result.renameTo(random);
        newBin.close();

        out.deleteOnExit();
        //result.deleteOnExit();
        outputFile.deleteOnExit();
        result.delete();
        myWriter.close();
        instream.close();
        outstream.close();

    }

    /**
     * to initialize Record class and store value 
     * into the class.
     * @param raf the file that reads from
     * @return Record 
     * @throws IOException exception
     */
    private static Record Record(RandomAccessFile raf) throws IOException {
        byte[] arr = new byte[8];
        int numPut = raf.read(arr);
        if (numPut != -1) {
            return new Record(arr);
        }
        return null;
    }

    /**
     * the method that converts the byte array to integer
     * @param intBytes byte array
     * @return integer
     */
    public static int convertByteArrayToInteger(byte[] intBytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.put(intBytes);
        byteBuffer.flip();
        return byteBuffer.getInt();
    }

    /**
     * the method that converts byte array to float.
     * @param floatBytes byte array/
     * @return float number 
     */
    public static float convertByteArrayToFloat(byte[] floatBytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.put(floatBytes);
        byteBuffer.flip();
        return byteBuffer.getFloat();
    }

}
