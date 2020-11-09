package Project3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class MergeSort {

    public static File multiwayMerge(File readFrom, 
        File writeTo, 
        ArrayList<Integer> runSizes) throws IOException {
        MinHeapTree<Record> heap = new MinHeapTree<Record>();
        int currRunSize = 0;
        int numRunsCompleted = 0;
        RandomAccessFile read = new RandomAccessFile(readFrom, "r");
        RandomAccessFile write = new RandomAccessFile(writeTo, "rw");
        FileWriter write2 = new FileWriter("a.txt");
        int[] offsets = new int[runSizes.size()];
        int counter = 0;
        for (int i = 0; i < runSizes.size(); i++) {
            offsets[i] = counter;
            counter += runSizes.get(i);
            System.out.println("runsize: " + counter);
        }
        ArrayList<Integer> newRuns = new ArrayList<Integer>();
        while (numRunsCompleted < runSizes.size()) {
            int thisNumRuns;
            if(numRunsCompleted + 4 <= runSizes.size()) {
                 thisNumRuns = 4;
            }
            else {
                 thisNumRuns = runSizes.size() % 4;
            }

            int[] numInHeap = new int[thisNumRuns];
            int[] numInOut = new int[thisNumRuns];
            int[] theseOffsets = new int[thisNumRuns];
            int[] numInRuns = new int[thisNumRuns];
            for (int i = 0; i < thisNumRuns; i++) {
                theseOffsets[i] = offsets[numRunsCompleted + i];
                numInHeap[i] = 0;
                numInOut[i] = 0;
                numInRuns[i] = 0;
            }
            currRunSize = 0;
            System.out.println("thisNumRuns: " + thisNumRuns);
            for (int i = 0; i < thisNumRuns; i++) {
                int numInBlock;
                int numInRun = runSizes.get(numRunsCompleted + i).intValue();
                numInRuns[i] = numInRun;
                if(numInRun >= 1024) {
                    numInBlock = 1024;
                }
                else {
                    numInBlock = numInRun;
                }
                Record[] thisRun = new Record[numInBlock];
                //System.out.println("numInBlock:  " + numInBlock);
                byte[] bArray = new byte[numInBlock * 8];
                read.seek(theseOffsets[i] * 8);
                read.read(bArray);
                System.out.println("numInBlock: " + numInBlock);
                for (int j = 0; j < numInBlock; j++) {
                    byte[] arr = new byte[8];
                    for (int k = 0; k < 8; k++) {
                        arr[k] = bArray[(j * 8) + k];
                    }
                    thisRun[j] = new Record(arr, i);
                    //System.out.println("run: " + thisRun[j].getValue());
                }
                for (int j = 0; j < numInBlock; j++) {
                    heap.insert(thisRun[j]);
                }
                numInHeap[i] = numInBlock;
                currRunSize += numInRun;
            }
            newRuns.add(Integer.valueOf(currRunSize));
            Record[] outputBuffer = new Record[1024];
            int idx = 0;
            int z = 0;
            while (heap.heapsize() > 0) {
                Record min = heap.getMin();
                heap.removeMin();
                outputBuffer[idx] = min;
                
                if (idx == 1023) {
                    idx = 0;
                    for (int i = 0; i < 1024; i++) {
                        write.write(outputBuffer[i].getTotal());
                        write2.write( String.valueOf(outputBuffer[i].getValue()) + "\n");
                        //System.out.println(outputBuffer[i].getValue());
                       
                    }
                }
                else {
                    idx++;
                }
                int thisMinRun = min.getRunNum();
                int numInBlock;
                numInHeap[thisMinRun] -= 1;
                numInOut[thisMinRun] += 1;
                if (numInHeap[thisMinRun] == 0 
                    && numInOut[thisMinRun] < numInRuns[thisMinRun]) {
                    if(numInRuns[thisMinRun] 
                        - numInOut[thisMinRun] >= 1024) {
                        numInBlock = 1024;
                    } else {
                        numInBlock = numInRuns[thisMinRun] - numInOut[thisMinRun];
                    }
                    Record[] thisRun = new Record[numInBlock];
                    byte[] bArray = new byte[numInBlock * 8];
                    read.seek(
                        (theseOffsets[thisMinRun] 
                            + numInOut[thisMinRun]) * 8);
                    read.read(bArray);
                    for (int j = 0; j < numInBlock; j++) {
                        byte[] arr = new byte[8];
                        for (int k = 0; k < 8; k++) {
                            arr[k] = bArray[(j * 8) + k];
                        }
                        thisRun[j] = new Record(arr, thisMinRun);
                        //System.out.println(thisRun[j].getValue());
                    }
                    for (int j = 0; j < numInBlock; j++) {
                        heap.insert(thisRun[j]);
                    }
                    numInHeap[thisMinRun] = numInBlock;
                }
            }
            if (idx != 0) {
                System.out.println("maybe?");
                for (int i = 0; i < idx; i++) {
                    write.write(outputBuffer[i].getTotal());
                    //System.out.println("idx: " + outputBuffer[i].getValue());
                    
                }
            }
            numRunsCompleted += thisNumRuns;
        }
        runSizes = newRuns;
        read.close();
        write.close();
        readFrom.delete();
  
        if (runSizes.size() > 1) {
            //System.out.println("runSize: " + runSizes.size());
            return multiwayMerge(writeTo, readFrom, runSizes);
        }
        else {
            return writeTo;
        }
    }
    
}