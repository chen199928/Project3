package Project3;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author youweichen and honghao zhang
 *         byte array class that store and add it to the tree.
 * @version 11/13/2020
 */
public class ByteArray {

    private byte[] byteArray1;
    private RandomAccessFile inputFile;
    private int byteOffset;
    private int blockVolume;
    private int numRun;
    private MinHeapTree<Record> heap;

    /**
     * byte array constructor
     * 
     * @param blockVolume
     *            the volume of the block
     * @param read
     *            read file
     * @param offsets
     *            integer offset.
     * @param numRun
     *            the number of run
     * @param heap
     *            heap tree.
     */
    public ByteArray(
        int blockVolume,
        RandomAccessFile read,
        int offsets,
        int numRun,
        MinHeapTree<Record> heap) {
        this.blockVolume = blockVolume;
        this.heap = heap;
        this.numRun = numRun;
        int size = blockVolume * 8;
        byteArray1 = new byte[size];
        inputFile = read;
        byteOffset = offsets * 8;
    }


    /**
     * add to the tree.
     * 
     * @throws IOException
     *             exception
     */
    public void addToTree() throws IOException {
        inputFile.seek(byteOffset);
        inputFile.read(byteArray1);

        for (int j = 0; j < blockVolume; j++) {
            byte[] arr = new byte[8];
            for (int k = 0; k < 8; k++) {
                arr[k] = byteArray1[(j * 8) + k];
            }
            Record record = new Record(arr, numRun);
            heap.insert(record);
        }
    }
}
