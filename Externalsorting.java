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


    public static void main(String[] args) throws IOException {

        File inputFile = new File(args[0]);
        File outputFile = new File("temp.txt");
        FileWriter myWriter = new FileWriter(outputFile);
        File out = new File(args[1]);
        ArrayList<Integer> runLengths = ReplacementSelection
            .replacementSelectionSort(inputFile, outputFile);

        // return a temp file
        File result = MergeSort.multiwayMerge(outputFile, out, runLengths);
        RandomAccessFile raf = new RandomAccessFile(result, "r");
        int numBlocks = ((int)raf.length()) / 8 / 1024;
        if (((int)raf.length()) / 8 % 1024 != 0) {
            numBlocks++;
        }
        for (int i = 0; i < numBlocks; i++) {
            int byte1 = i * 8192;
            raf.seek(byte1);
            Record here = nextRecord(raf);
            if (here != null) {
                System.out.print(Integer.toString(here.getKey()) + " " + Float
                    .toString(here.getValue()));
                if ((i + 1) % 4 == 0) {
                    System.out.print("\n");
                }
                else {
                    System.out.print(", ");
                }
            }
        }
        raf.close();
        // out.delete();
        myWriter.close();

    }


    private static Record nextRecord(RandomAccessFile raf) throws IOException {
        byte[] arr = new byte[8];
        int numPut = raf.read(arr);
        if (numPut != -1) {
            return new Record(arr);
        }
        return null;
    }

}
