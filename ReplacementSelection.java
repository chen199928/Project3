package Project3;
import java.io.File;
import java.io.FileWriter;
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

            FileWriter write2 = new FileWriter("FileOutput.txt");
            MinHeapTree<Record> heap = new MinHeapTree<Record>();
            ArrayList<Integer> runSizes = new ArrayList<Integer>();
            int currRunSize = 0;
            fillHeap(heap, read);

            int lineNum = 0;
            int check = (int)(read.length() / (1024 * 8));
            Record[] inputBuffer = makeInput(read);
            Record[] outputBuffer = new Record[1024];
            if (check <= 16) {
                ArrayList<String> list = heap.print();
                for (int i = 0; i < list.size(); i++) {
                    // write.write(outputBuffer[i].getValue());
                    
                    String result = String.valueOf(list.get(i));
                    write2.write(result + "\n");
                    //System.out.println(outputBuffer[i].getValue());
                 }
            }
            
            else {
                while (inputBuffer != null) {
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
                                //System.out.println("curr: " + currRunSize);
                                runSizes.add(Integer.valueOf(currRunSize));
                                currRunSize = 0;
                                heap.setHeapSize(1024*16);
                                heap.buildheap();
                            }
                        }
                    }
                  
                    for (int i = 0; i < 1024; i++) {
                        write.write(outputBuffer[i].getTotal());
                       String result = String.valueOf(outputBuffer[i].getKey() + "   "+ outputBuffer[i].getValue());
                       write2.write(result + "\n");
                    }
                    inputBuffer = makeInput(read);
                }
            }
            clearHeap(heap, runSizes, currRunSize, write, write2);
            read.close();
            write.close();
            write2.close();
            given.delete();
           
            return runSizes;
        }
        
        private static void clearHeap(MinHeapTree heap, 
            ArrayList<Integer> runSizes, 
            int currRunSize, 
            RandomAccessFile write, FileWriter w2) throws IOException {
            int hidden = 1024*16 - heap.heapsize();
            for (int j = 0; j < 16; j++) {
                Record[] outputBuffer = new Record[1024];
                for (int i = 0; i < 1024; i++) {
                    Record out = (Record)heap.getMin();
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
                    String result = String.valueOf(outputBuffer[i].getKey() + "   "+ outputBuffer[i].getValue());
                    w2.write(result + "\n");
                }
            }
            runSizes.add(Integer.valueOf(currRunSize));
        }
        

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
                return null;
            }
            return input;
        }
        

        private static Record nextRecord(byte[] bArray, 
            int offset) throws IOException {
            byte[] arr = new byte[8];
            for (int i = 0; i < 8; i++) {
                arr[i] = bArray[offset + i];
            }
            return new Record(arr);
        }
        
        private static void fillHeap(MinHeapTree<Record> heap, 
            RandomAccessFile read) throws IOException {
            int max;
            int check = (int)(read.length() / 8192);
            if (check <= 16) {
                max = check;
            }
            else {
                max = 16;
            }
            for (int j = 0; j < max; j++) {
                Record[] input = makeInput(read);
                for (int i = 0; i < 1024; i++) {
                    heap.insert(input[i]);
                }
            }
        }

        
}

