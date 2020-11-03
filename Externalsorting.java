package Project3;

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
            byte[] intarray = new byte[4];
            byte[] floatarray = new byte[4];
            rfile.seek(pointer);
            rfile.read(intarray);
            // System.out.print();
            // break; 
            pointer = pointer + 4;
            rfile.seek(pointer);
            rfile.read(floatarray);
            pointer = pointer + 4;
            record = new Record(convertByteArrayToInteger(intarray),
                convertByteArrayToFloat(floatarray));
            
            tree.insert(record);
            
        }
           for(int i = 1 ; i <= tree.getSize(); i ++) {
               myWriter.write(tree.getHeap()[i].getKey() + "    ");
               myWriter.write(tree.getHeap()[i].getValue() + "\n");
           }
        myWriter.close();

    }


    public static int convertByteArrayToInteger(byte[] intBytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
        byteBuffer.put(intBytes);
        byteBuffer.flip();
        return byteBuffer.getInt();
    }


    public static float convertByteArrayToFloat(byte[] floatBytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.put(floatBytes);
        byteBuffer.flip();
        return byteBuffer.getFloat();
    }
}
