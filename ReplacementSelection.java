package Project3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * 
 * @author youwei(adam)chen and honghao zhang
 *         to process the records stored in the tree
 *         and sort them in each run.
 * @version 11/13/2020
 */
public class ReplacementSelection {
    /**
     * to implement the replacement selection
     * sort.
     * 
     * @param readFile
     *            input file
     * @param writeFile
     *            output file
     * @return the list that each index stands for
     *         the number of records in each run.
     * @throws IOException
     *             exception.
     */
    static ArrayList<Integer> replacementSelectionSort(
        RandomAccessFile readFile,
        RandomAccessFile writeFile)
        throws IOException {

        MinHeapTree<Record> heap = new MinHeapTree<Record>();
        ArrayList<Integer> run = new ArrayList<Integer>();
        int currRun = 0;
        int check = (int)(readFile.length() / (1024 * 8));
        int max;
        if (check <= 16) {
            max = check;
        }
        else {
            max = 16;
        }
        for (int j = 0; j < max; j++) {
            Record[] input = nextInput(readFile);
            for (int i = 0; i < 1024; i++) {
                heap.insert(input[i]);
            }
        }
        Record[] inputBuffer = nextInput(readFile);
        Record[] outputBuffer = new Record[1024];

        if (check > 16) {
            while (inputBuffer != null) {
                for (int i = 0; i < 1024; i++) {
                    Record output = heap.getMin();
                    outputBuffer[i] = output;
                    Record inputRecord = inputBuffer[i];
                    heap.setMin(inputRecord);
                    currRun++;
                    if (inputRecord.compareTo(output) < 0) {
                        boolean result = heap.removeMin();
                        if (result) {
                            run.add(Integer.valueOf(currRun));
                            currRun = 0;
                            heap.setHeapSize(1024 * 16);
                            heap.buildheap();
                        }
                    }
                    else {
                        heap.siftdown(0);
                    }
                }
                for (int i = 0; i < 1024; i++) {
                    writeFile.write(outputBuffer[i].getRecord());
                }
                inputBuffer = nextInput(readFile);
            }
        }
        if (heap != null) {

            clear(heap, run, currRun, writeFile, check);
        }
        readFile.close();
        writeFile.close();
        return run;
    }


    /**
     * the clear method
     * 
     * @param heap
     *            heap tree
     * @param run
     *            the list that stores the # of run.
     * @param currRun
     *            current run number
     * @param writeFile
     *            output file
     * @param check
     *            to make sure it is limited by 16.
     * @throws IOException
     *             exception
     */
    private static void clear(
        MinHeapTree<Record> heap,
        ArrayList<Integer> run,
        int currRun,
        RandomAccessFile writeFile,
        int check)
        throws IOException {
        int hide = 1024 * 16 - heap.heapsize();
        // System.out.println(heap.heapsize());
        int max;
        if (check <= 16) {
            max = check;
        }
        else {
            max = 16;
        }
        for (int j = 0; j < max; j++) {
            Record[] outputBuffer = new Record[1024];
            for (int i = 0; i < 1024; i++) {
                Record out = (Record)heap.getMin();
                if (out == null) {
                    i--;
                    run.add(Integer.valueOf(currRun));
                    currRun = 0;
                    heap.setHide(hide);
                }
                else {
                    outputBuffer[i] = out;
                    heap.removeMin();
                    currRun++;
                }
            }
            for (int i = 0; i < 1024; i++) {
                Record read = outputBuffer[i];
                writeFile.write(read.getRecord());
            }
        }

        run.add(Integer.valueOf(currRun));
    }


    /**
     * to read the file and store the
     * record into the input buffer.
     * 
     * @param readFile
     *            the file to read
     * @return inputBuffer array
     * @throws IOException
     *             exception.
     */
    private static Record[] nextInput(RandomAccessFile readFile)
        throws IOException {
        Record[] inputBuffer = new Record[1024];
        byte[] byteArray = new byte[8192];
        int numIn = readFile.read(byteArray);
        if (numIn == -1) {
            return null;
        }
        else {
            for (int i = 0; i < 1024; i++) {
                Record result = record(byteArray, i * 8);
                inputBuffer[i] = result;
            }
            return inputBuffer;
        }

    }


    /**
     * the way to extract each key and value from the
     * byte array and create Record class to store each record.
     * 
     * @param array
     *            array
     * @param offset
     *            offset
     * @return the record array.
     * @throws IOException
     *             exception.
     */
    private static Record record(byte[] array, int offset) throws IOException {
        byte[] arr = new byte[8];
        for (int i = 0; i < 8; i++) {
            arr[i] = array[offset + i];
        }
        return new Record(arr);
    }

}
