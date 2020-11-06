
import java.nio.ByteBuffer;

public class Record implements Comparable<Record>{
    int key;
    float value;
    byte[] k;
    
    /**
     * for the first initialization in the tree.
     */
    public Record() {
        key = 0;
        value = 0;
        k = null;
    }
    public Record(byte[] record) {
        k = record;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(record);
        buffer.flip();
        this.key = buffer.getInt();
        this.value = buffer.getFloat();
    }
    
    public int getKey() {
        return key;
    }
    
    public float getValue() {
        return value;
    }
    
    public int findID(float value) {
       return key; 
    }
    @Override
    public int compareTo(Record o) {
        // TODO Auto-generated method stub
        if (key - o.getKey() < 0) {
            return -1;
        }
        else if (key == o.getKey()) {
            return 0;
        }
        else {
            return 1;
        }
    }
    
    public byte[] getTotal() {
        return k;
    }
}
