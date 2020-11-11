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

public class Externalsorting {

    public static void main(String[] args) throws IOException {
        //FileWriter test = new FileWriter("test128.txt");
        File inputFile = new File(args[0]);
        File outputFile = new File("temp.txt");
        FileWriter myWriter = new FileWriter(outputFile);
        File out = new File(args[1]);
        RandomAccessFile read = new RandomAccessFile(inputFile, "r");
        int numBlocks = ((int)read.length()) / 8 / 1024;
        int numRun = ((int)read.length()) / 8 % 1024;
        RandomAccessFile write = new RandomAccessFile(outputFile, "rw");
        int countComma = 0;
        ArrayList<Integer> run = ReplacementSelection.replacementSelectionSort(
            read, write);

        // return a temp file
        File result = MergeSort.multiwayMerge(outputFile, out, run);
        String count;

        RandomAccessFile newBin = new RandomAccessFile(result, "r");
        int pointer = 0;
//        while (pointer <= newBin.length() - 1) {
//            byte[] intarray = new byte[4];
//            byte[] floatarray = new byte[4];
//            newBin.seek(pointer);
//            newBin.read(intarray);
//            // System.out.print(); // System.out.print();
//            // break; // break;
//            pointer = pointer + 4;
//            newBin.seek(pointer);
//            newBin.read(floatarray);
//            pointer = pointer + 4;
//            test.write(String.valueOf(convertByteArrayToInteger(intarray)));
//            test.write(String.valueOf(convertByteArrayToFloat(floatarray)));
//            test.write("\n");
//
//        }

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

        newBin.close();
        // out.delete();
        myWriter.close();

    }


    private static Record Record(RandomAccessFile raf) throws IOException {
        byte[] arr = new byte[8];
        int numPut = raf.read(arr);
        if (numPut != -1) {
            return new Record(arr);
        }
        return null;
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
