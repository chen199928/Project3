package Project3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class MergeSort {

    public static File multiwayMerge(File inputFile, 
        File outputFile, 
        ArrayList<Integer> list) throws IOException {
        MinHeapTree<Record> heap = new MinHeapTree<Record>();
        int current = 0;
        int completedRun = 0;

        RandomAccessFile read = new RandomAccessFile(inputFile, "r");
        RandomAccessFile write = new RandomAccessFile(outputFile, "rw");
        ArrayList<Integer> refresh = new ArrayList<Integer>();
        int maxRun = list.size();
        int[] offsets = new int[maxRun];
        addOffsets(offsets, maxRun, list);
       
        while (completedRun < maxRun) {
            int numRuns =  getNumRuns(completedRun,maxRun);
            int[] heapVolume = new int[numRuns];
            int[] bufferVolume = new int[numRuns];
            int[] newOffsets = new int[numRuns];
            int[] runVolume = new int[numRuns];
            for (int i = 0; i < numRuns; i++) {
                newOffsets[i] = offsets[completedRun + i];
            }
            current = 0;

            for (int i = 0; i < numRuns; i++) {

               
                runVolume[i] = list.get(completedRun + i);
                int blockVolume = limitUpperBound(runVolume[i]);
                Record[] run = new Record[blockVolume];
                //System.out.println("numInBlock:  " + numInBlock);
                byte[] byteArray = new byte[blockVolume * 8];
                read.seek(newOffsets[i] * 8);
                read.read(byteArray);

                for (int j = 0; j < blockVolume; j++) {
                    byte[] arr = new byte[8];
                    for (int k = 0; k < 8; k++) {
                        arr[k] = byteArray[(j * 8) + k];
                    }
                    run[j] = new Record(arr, i);
                    heap.insert(run[j]);
                }
                heapVolume[i] = blockVolume;
                current += runVolume[i];
            }
            refresh.add(current);
            Record[] outputBuffer = new Record[1024];
            int position = 0;
            while (heap.heapsize() > 0) {
                Record record = heap.getMin();
                heap.removeMin();
                outputBuffer[position] = record;
                
                if (position == 1023) {
                    position = 0;
                    for (int i = 0; i < 1024; i++) {
                        write.write(outputBuffer[i].getRecord());                      
                    }
                }
                else {
                    position++;
                }
                int recordRun = record.getRun();
                int blockVolume;
                heapVolume[recordRun] -= 1;
                bufferVolume[recordRun] += 1;
                if (heapVolume[recordRun] == 0 
                    && bufferVolume[recordRun] < runVolume[recordRun]) {
                    if(runVolume[recordRun] 
                        - bufferVolume[recordRun] >= 1024) {
                        blockVolume = 1024;
                    } else {
                        blockVolume = runVolume[recordRun] - bufferVolume[recordRun];
                    }
                    Record[] run = new Record[blockVolume];
                    byte[] byteArray = new byte[blockVolume * 8];
                    read.seek(
                        (newOffsets[recordRun] 
                            + bufferVolume[recordRun]) * 8);
                    read.read(byteArray);

                    for (int j = 0; j < blockVolume; j++) {
                        byte[] arr = new byte[8];
                        for (int k = 0; k < 8; k++) {
                            arr[k] = byteArray[(j * 8) + k];
                        }
                        run[j] = new Record(arr, recordRun);
                        heap.insert(run[j]);
                    }
                    heapVolume[recordRun] = blockVolume;
                }
            }
            if (position != 0) {
                for (int i = 0; i < position; i++) {
                    write.write(outputBuffer[i].getRecord());  
                }
            }
            completedRun += numRuns;
        }
        list = refresh;
        read.close();
        write.close();
        //inputFile.delete();
  
        if (list.size() > 1) {
            return multiwayMerge(outputFile, inputFile, list);
        }
        else {
            return outputFile;
        }
    }
    
    public static void addOffsets(int[] offsets, int maxRun, ArrayList<Integer> runSizes) {
        int count = 0;
        for (int i = 0; i < maxRun; i++) {
            offsets[i] = count;
            count += runSizes.get(i);
        }
    }

    
    public static int getNumRuns(int numRunsCompleted, int maxRun) {
        int result;
        if(numRunsCompleted + 4 <= maxRun) {
            result = 4;
       }
       else {
            result = maxRun % 4;
       }
       return result;
    }
    
    public static int limitUpperBound(int numInRun) {
        int result;
        if(numInRun >= 1024) {
            result = 1024;
        }
        else {
            result = numInRun;
        }
        return result;
    }
    
}