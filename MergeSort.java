package Project3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * 
 * @author youweichen and Honghao Zhang
 *         MergeSort is to overall sort the blocks on different
 *         runs in the file.
 * @version 11/13/2020
 */
public class MergeSort {
    /**
     * 
     * @param inputFile
     *            input file
     * @param outputFile
     *            output file
     * @param list
     *            list contains the volume of each run
     * @return sorted file
     * @throws IOException
     *             Exception
     *             multiwayMerge is to sort the sorted blocks on each run.
     *             multiwayMerge is to combine the number of runs to one sorted
     *             run.
     */
    public static File multiwayMerge(
        File inputFile,
        File outputFile,
        ArrayList<Integer> list)
        throws IOException {
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
            int numRuns = getNumRuns(completedRun, maxRun);
            int[] heapVolume = new int[numRuns];
            int[] bufferVolume = new int[numRuns];
            int[] runVolume = new int[numRuns];
            current = 0;

            for (int i = 0; i < numRuns; i++) {

                runVolume[i] = list.get(completedRun + i);
                int blockVolume = limitUpperBound(runVolume[i]);
                ByteArray array = new ByteArray(blockVolume, read,
                    offsets[completedRun + i], i, heap);
                array.addToTree();

                heapVolume[i] = blockVolume;
                current += runVolume[i];
            }

            refresh.add(current);
            Buffer buffer = new Buffer(write);
            while (heap.heapsize() > 0) {
                Record record = heap.getMin();
                heap.removeMin();
                buffer.insert(record);

                int recordRun = record.getRun();
                int blockVolume;
                heapVolume[recordRun] -= 1;
                bufferVolume[recordRun] += 1;
                if (heapVolume[recordRun] == 0
                    && bufferVolume[recordRun] < runVolume[recordRun]) {
                    if (runVolume[recordRun]
                        - bufferVolume[recordRun] >= 1024) {
                        blockVolume = 1024;
                    }
                    else {
                        blockVolume = runVolume[recordRun]
                            - bufferVolume[recordRun];
                    }
                    int offsetsTotal = offsets[completedRun + recordRun]
                        + bufferVolume[recordRun];
                    ByteArray array = new ByteArray(blockVolume, read,
                        offsetsTotal, recordRun, heap);
                    array.addToTree();

                    heapVolume[recordRun] = blockVolume;
                }
            }

            buffer.dumpToFile();
            completedRun += numRuns;
        }
        list = refresh;
        read.close();
        write.close();

        if (list.size() > 1) {
            return multiwayMerge(outputFile, inputFile, list);
        }
        else {
            return outputFile;
        }
    }


    /**
     * to add the offsets
     * 
     * @param offsets
     *            the number of records each run contains
     * @param maxRun
     *            the max number of run
     * @param list
     *            the list that stores the number of records each run
     *            has.
     */
    public static void addOffsets(
        int[] offsets,
        int maxRun,
        ArrayList<Integer> list) {
        int count = 0;
        for (int i = 0; i < maxRun; i++) {
            offsets[i] = count;
            count += list.get(i);
        }
    }


    /**
     * to get the run
     * 
     * @param numRunsCompleted
     *            completed run
     * @param maxRun
     *            the max number of run
     * @return the run
     */
    public static int getNumRuns(int numRunsCompleted, int maxRun) {
        int result;
        if (numRunsCompleted + 4 <= maxRun) {
            result = 4;
        }
        else {
            result = maxRun % 4;
        }
        return result;
    }


    /**
     * to make sure it does not exceed the bound
     * 
     * @param numInRun
     *            input number
     * @return output number that does not exceed
     *         the bound.
     */
    public static int limitUpperBound(int numInRun) {
        int result;
        if (numInRun >= 1024) {
            result = 1024;
        }
        else {
            result = numInRun;
        }
        return result;
    }

}
