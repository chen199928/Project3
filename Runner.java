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
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.io.IOException;

public class Runner {
    static final int NumRecs = 1024; // Each record holds 8 bytes. Each block
                                     // has 8192 bytes

    /** Initialize the random variable */
    static private Random value = new Random(); // Hold the Random class object
    static final int capacity = 1024 * 8;

    static int randInt() {
        return value.nextInt(Integer.MAX_VALUE);
    }


    static float randFloat() {
        return value.nextFloat() * Float.MAX_VALUE;
    }


    public static void main(String[] args) throws IOException {

        // DataInputStream input = new DataInputStream(new
        // BufferedInputStream(new FileInputStream(args[0])));
        RandomAccessFile rfile = new RandomAccessFile(args[0], "r");
        FileWriter myWriter = new FileWriter(args[1]);
        MinHeapTree heap = new MinHeapTree();
        Record record;
        byte[] inputArray = new byte[capacity];
        byte[] outputArray = new byte[capacity];
        ArrayList<Record> list = new ArrayList<Record>();
        // rfile.readLine();
        int pointer = 0;
        while (pointer <= rfile.length() - 1) {

            byte[] intarray = new byte[4];
            byte[] floatarray = new byte[4];
            rfile.seek(pointer);
            rfile.read(intarray);

            pointer = pointer + 4;
            rfile.seek(pointer);
            rfile.read(floatarray);
            pointer = pointer + 4;
            record = new Record(convertByteArrayToInteger(intarray),
                convertByteArrayToFloat(floatarray));
            list.add(record);

        }

        for (int i = 0; i < list.size(); i++) {
            myWriter.write(list.get(i).getKey() + "    ");
            myWriter.write(list.get(i).getValue() + "\n");
        }

        rfile.close();
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
