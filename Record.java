package Project3;

import java.nio.ByteBuffer;

public class Record implements Comparable<Record>{
    int key;
    float value;
    byte[] byteArray;
    int runNum;

    public Record() {
        key = 0;
        value = 0;
        byteArray = null;
    }
    
    
    public Record(byte[] record) {
        byteArray = record;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(record);
        buffer.flip();
        this.key = buffer.getInt();
        this.value = buffer.getFloat();
        runNum = 0;
    }
    
    
    public Record(byte[] record, int num) {
        byteArray = record;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(record);
        buffer.flip();
        this.key = buffer.getInt();
        this.value = buffer.getFloat();
        runNum = num;
    }
    
    public int getRun() {
        return runNum;
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
    
    public byte[] getRecord() {
        return byteArray;
    }
    @Override
    public int compareTo(Record o) {
        // TODO Auto-generated method stub
        if (value - o.getValue() < 0) {
            return -1;
        }
        else if (value == o.getValue()) {
            return 0;
        }
        else {
            return 1;
        }
    }
    

    public String toString() {
        return this.getKey() + "     " + this.getValue();
    }
}
