import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ReplacementSelection {
    static final int FIXEDSIZE = 8192;
    private Record[] inputBuffer;
    private Record[] outputBuffer;
    private MinHeapTree tree;

        /**
         * Static method for performing a replacement selection sort
         * 
         * @param given to read
         * @param sorted to write
         * @return the arraylist of runs
         * @throws IOException
         */
        public static ArrayList<Integer> replacementSelectionSort(
            File given, File sorted) throws IOException {
            RandomAccessFile read = new RandomAccessFile(given, "r");
            RandomAccessFile write = new RandomAccessFile(sorted, "rw");
            MinHeapTree heap = new MinHeapTree(16*1024);
            ArrayList<Integer> runSizes = new ArrayList<Integer>();
            int currRunSize = 0;
            fillHeap(heap, read);
            Record[] inputBuffer = makeInput(read);
            Record[] outputBuffer = new Record[1024];
            
            System.out.println(inputBuffer == null);
            while (inputBuffer != null) {
                System.out.println("222222");
                for (int i = 0; i < 1024; i++) {
                    Record out = heap.getMin();
                    outputBuffer[i] = out;
                    currRunSize++;
                    Record in = inputBuffer[i];
                    heap.setMin(in);
                    if (in.compareTo(out) >= 0) {
                        heap.siftdown(0);
                    }
                    else {
                        boolean result = heap.removeMin();
                        if (result) {
                            runSizes.add(Integer.valueOf(currRunSize));
                            currRunSize = 0;
                            heap.setHeapSize(1024*16);
                            heap.buildheap();
                        }
                    }
                }
              
                for (int i = 0; i < 1024; i++) {
                   // write.write(outputBuffer[i].getValue());
                   write.writeFloat(outputBuffer[i].getValue());
                   System.out.println(outputBuffer[i].getValue());
                }
                inputBuffer = makeInput(read);
            }
            clearHeap(heap, runSizes, currRunSize, write);
            read.close();
            write.close();
           // given.delete();
            return runSizes;
        }
        
        /**
         * Sends the contents of heap to outputs
         * 
         * @param heap to use
         * @param runSizes to check
         * @param currRunSize of continuing run
         * @param write to file
         * @throws IOException
         */
        private static void clearHeap(MinHeapTree heap, 
            ArrayList<Integer> runSizes, 
            int currRunSize, 
            RandomAccessFile write) throws IOException {
            int hidden = 1024*16 - heap.heapsize();
            for (int j = 0; j < 16; j++) {
                Record[] outputBuffer = new Record[1024];
                for (int i = 0; i < 1024; i++) {
                    Record out = heap.getMin();
                    if (out != null) {
                        outputBuffer[i] = out;
                        currRunSize++;
                        heap.removeMin();
                    }
                    else {
                        i--;
                        runSizes.add(Integer.valueOf(currRunSize));
                        currRunSize = 0;
                        heap.setHidden(hidden);
                    }
                }
                for (int i = 0; i < 1024; i++) {
                    Record r = outputBuffer[i];
                    write.write(r.getTotal());
                }
            }
            runSizes.add(Integer.valueOf(currRunSize));
        }
        
        /**
         * Makes an input buffer
         * 
         * @param read from file
         * @return input buffer
         * @throws IOException
         */
        private static Record[] makeInput(
            RandomAccessFile read) throws IOException {
            Record[] input = new Record[1024];
            byte[] bArray = new byte[8192];
            int numPut = read.read(bArray);
            
            if (numPut != -1) {
             
                for (int i = 0; i < 1024; i++) {
                    Record result = nextRecord(bArray, i * 8);
                    input[i] = result;
                }
            }
            else {
                //System.out.println("numPut is: " + numPut);
                return null;
            }
            return input;
        }
        
        /**
         * Reads next record from file
         * 
         * @param bArray to be read
         * @param offset to start
         * @return next record
         * @throws IOException
         */
        private static Record nextRecord(byte[] bArray, 
            int offset) throws IOException {
            byte[] arr = new byte[8];
            for (int i = 0; i < 8; i++) {
                arr[i] = bArray[offset + i];
            }
            return new Record(arr);
        }
        
        /**
         * Fills the heap with 16 blocks of data
         * 
         * @param heap to fill
         * @param read from file
         * @throws IOException
         */
        private static void fillHeap(MinHeapTree heap, 
            RandomAccessFile read) throws IOException {
            for (int j = 0; j < 16; j++) {
                Record[] input = makeInput(read);
                for (int i = 0; i < 1024; i++) {
                    heap.insert(input[i]);
                }
            }
        }
        
}

