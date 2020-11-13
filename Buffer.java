import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
/**
 * Buffer class that is used to store 
 * and writes to the file;
 * @author youweichen and honghao zhang
 *
 */
public class Buffer {
    private byte[] bytes;
    private int order;
    private ArrayList<Record> list;
    private RandomAccessFile outputFile;
    
    /**
     * the buffer constructor.
     * @param outputFile output file
     * @throws IOException exception
     */
    public Buffer(RandomAccessFile outputFile) throws IOException {
        list = new ArrayList<Record>();       
        this.outputFile = outputFile;
    }
    
    /**
     * dump to the file 
     * @throws IOException exception
     */
    public void dumpToFile() throws IOException {
        while (!this.isEmpty()) {
            outputFile.write(list.get(0).getRecord());
 
            list.remove(0);
        }

    }
    
    /**
     * to check if it is full
     * @return true or false 
     * @throws IOException exception
     */
     private boolean isFull() throws IOException {
        if (list.size() == 1024) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * to insert the record into the buffer.
     * @param record record 
     * @throws IOException exception.
     */
    public void insert(Record record) throws IOException {
        list.add(record);
        if (this.isFull()) {
            dumpToFile();
        }

    }
    
    /**
     * to check if it is empty
     * @return
     */
    public boolean isEmpty() {
        return list.size() == 0;
    }
    
    /**
     * to get the order
     * @return integer
     */
    public int getOrder() {
        return order;
    }
    
    /**
     * to get the bytes
     * @return bytes.
     */
    public byte[] getBytes() {
        return bytes;
    }
}
