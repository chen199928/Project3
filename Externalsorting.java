package Project3;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

public class Externalsorting {
    static final int NumRecs = 1024 * 8; // Each record holds 8 bytes. Each block
                                     // has 1024 bits

    /** Initialize the random variable */
    static private Random value = new Random(); // Hold the Random class object

    static int randInt() {
        return value.nextInt(Integer.MAX_VALUE);
    }


    static float randFloat() {
        return value.nextFloat() * Float.MAX_VALUE;
    }


    public static void main(String[] args) throws IOException {

       DataInputStream input = new DataInputStream(new BufferedInputStream(
            new FileInputStream(args[0])));
        File inputFile = new File(args[0]);
        RandomAccessFile rfile = new RandomAccessFile(inputFile, "r");
        System.out.println("rfile: " + rfile.length());
        File outputFile = new File(args[1]);
        System.out.println(rfile.length());
        FileWriter myWriter = new FileWriter(outputFile);
        ArrayList<Record> list = new ArrayList<Record>();
        Record record;
        
        
        
        MinHeapTree tree = new MinHeapTree(1024*16);
        // rfile.readLine();
        int pointer = 0;
        byte[] inputArray = new byte[NumRecs];   // ask if this one is right
        byte[] outputArray = new byte[NumRecs];  // ask if this one is right
      
        
        while (pointer <= rfile.length() - 1) {
           byte[] recordByte = new byte[8];
            rfile.seek(pointer);
            rfile.read(recordByte);
            pointer = pointer + 8;

            record = new Record(recordByte);
            //System.out.println(record.getValue());
        
            tree.insert(record);
            
        }
        File out = new File("x.bin");
        System.out.println(out.length());
        ArrayList<Integer> runLengths = 
            ReplacementSelection.replacementSelectionSort(inputFile, outputFile);
        System.out.println("output: " + outputFile.length());
        File result = MergeSort.multiwayMerge(outputFile, out, runLengths);
        resultsPrint(result);
         
   
        out.delete();
        myWriter.close();

    }
    private static Record nextRecord(RandomAccessFile raf) throws IOException {
        byte[] arr = new byte[8];
        int numPut = raf.read(arr);
        if (numPut == -1) {
            return null;
        }
        return new Record(arr);
    }
    private static void resultsPrint(File results) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(results, "r");
        int numRecs = ((int)raf.length()) / 8;
        int numBlocks = numRecs / 1024;
        System.out.println("numblock: " + numRecs);
        if (numRecs % 1024 != 0) {
            numBlocks++;
        }
        for (int i = 0; i < numBlocks; i++) {
            raf.seek(i * 8192);
            Record here = nextRecord(raf);
            System.out.print(Integer.toString(here.getKey()) 
                + " " + Float.toString(here.getValue()));
            if ((i + 1 ) % 4 == 0) {
                System.out.print("\n");
            }
            else {
                System.out.print(", ");
            }
        }
        raf.close();
    }

}
