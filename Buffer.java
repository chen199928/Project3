
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Buffer class that is used to store
 * and writes to the file;
 * 
 * @author youweichen and honghao zhang
 * @version 11/13/2020
 */
public class Buffer {
    private byte[] bytes;
    private int order;
    private ArrayList<Record> list;
    private RandomAccessFile outputFile;
    private int index;
    
    private ByteArrayOutputStream outputStream;
    /**
     * the buffer constructor.
     * 
     * @param outputFile
     *            output file
     * @throws IOException
     *             exception
     */
    public Buffer(RandomAccessFile outputFile) throws IOException {
        bytes = new byte[1024*8];
        list = new ArrayList<Record>();
        this.outputFile = outputFile;
        index = 0;
        outputStream = new ByteArrayOutputStream();
    }


    /**
     * dump to the file
     * 
     * @throws IOException
     *             exception
     */
    public void dumpToFile() throws IOException {
        byte result[] = outputStream.toByteArray( ); 
        outputFile.write(result);
        outputStream.reset();
    }


    /**
     * to check if it is full
     * 
     * @return true or false
     * @throws IOException
     *             exception
     */
    private boolean isFull() throws IOException {
        return list.size() == 1024;
    }


    /**
     * to insert the record into the buffer.
     * 
     * @param record
     *            record
     * @throws IOException
     *             exception.
     */
    public void insert(Record record) throws IOException {
        list.add(record);
        outputStream.write(record.getRecord());
        if (this.isFull()) {
            dumpToFile();
        }

    }


    /**
     * to check if it is empty
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return list.size() == 0;
    }


    /**
     * to get the order
     * 
     * @return integer
     */
    public int getOrder() {
        return order;
    }


    /**
     * to get the bytes
     * 
     * @return bytes.
     */
    public byte[] getBytes() {
        return bytes;
    }
}
