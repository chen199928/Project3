

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
        RandomAccessFile rfile = new RandomAccessFile(args[0], "r");
        FileWriter myWriter = new FileWriter(args[1]);
        ArrayList<Record> list = new ArrayList<Record>();
        Record record;
        MinHeapTree tree = new MinHeapTree();
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
            System.out.println(record.getValue());
        
            tree.insert(record);
            
        }
        
        System.out.println(rfile.length());
        //to check the size of the file.
        int check = (int)(rfile.length() / 8192);
        System.out.println(check);
        if (check <= 16) {
            //if it is less than 16 blocks, then no need to start replacement selection process.
            for(int i = 0 ; i <tree.getSize(); i ++) {
                myWriter.write(tree.getHeap()[i].getKey() + "    ");
                myWriter.write(tree.getHeap()[i].getValue() + "\n");
            }
        }
        else {
            //start the replacement selection process.
        }
         
   
         
        myWriter.close();

    }

}
