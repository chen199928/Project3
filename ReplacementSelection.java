package Project3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ReplacementSelection {
    static ArrayList<Integer> replacementSelectionSort(
        RandomAccessFile readFile,
        RandomAccessFile writeFile)
        throws IOException {

        FileWriter write2 = new FileWriter("FileOutput.txt");
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
//        if (check <= 16) {
//
//            ArrayList<String> list = heap.print();
//            for (int i = 0; i < list.size(); i++) {
//                String result = list.get(i);
//                write2.write(result + "\n");
//                // write.write(outputBuffer[i].getTotal());
//            }
//
//        }

        if(check > 16){
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
                    String result = String.valueOf(outputBuffer[i].getKey()
                        + "   " + outputBuffer[i].getValue());
                    write2.write(result + "\n");
                }
                inputBuffer = nextInput(readFile);
            }
        }
        if (heap != null) {

            clear(heap, run, currRun, writeFile, write2, check);
        }
        readFile.close();
        // System.out.println(writeFile.length());
        writeFile.close();
        write2.close();
        return run;
    }


    private static void clear(
        MinHeapTree<Record> heap,
        ArrayList<Integer> run,
        int currRun,
        RandomAccessFile writeFile,
        FileWriter w2,
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
                    String result = String.valueOf(read.getKey() + "   " + read
                        .getValue());
                    w2.write(result + "\n");
                }
            }
        
        run.add(Integer.valueOf(currRun));
    }


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
                Record result = Record(byteArray, i * 8);
                inputBuffer[i] = result;
            }
            return inputBuffer;
        }

    }


    private static Record Record(byte[] array, int offset) throws IOException {
        byte[] arr = new byte[8];
        for (int i = 0; i < 8; i++) {
            arr[i] = array[offset + i];
        }
        return new Record(arr);
    }

}
