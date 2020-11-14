package Project3;

import java.nio.ByteBuffer;

/**
 * 
 * @author youweichen and honghao zhang
 *         the record class is to store each line
 *         (integer and float value)from
 *         the file.
 * @version 11/13/2020
 */
public class Record implements Comparable<Record> {
    private int key;
    private float value;
    private byte[] byteArray;
    private int runNum;

    /**
     * Record constructor that take no paramter
     */
    public Record() {
        key = 0;
        value = 0;
        byteArray = null;
    }


    /**
     * it takes in byte array that contain integer key
     * and float value. We store that byte array into
     * the record class.
     * 
     * @param record
     *            byte array
     */
    public Record(byte[] record) {
        byteArray = record;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(record);
        buffer.flip();
        this.key = buffer.getInt();
        this.value = buffer.getFloat();
        runNum = 0;
    }


    /**
     * Record class that not only store the
     * integer and float value but also store the
     * run number that will be used later in the mergeSort.
     * 
     * @param record
     *            record
     * @param num
     *            the run number
     */
    public Record(byte[] record, int num) {
        byteArray = record;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(record);
        buffer.flip();
        this.key = buffer.getInt();
        this.value = buffer.getFloat();
        runNum = num;
    }


    /**
     * to get the run
     * 
     * @return integer type of run
     * 
     */
    public int getRun() {
        return runNum;
    }


    /**
     * to get the integer key
     * 
     * @return integer key
     */
    public int getKey() {
        return key;
    }


    /**
     * to get the float value
     * 
     * @return float value
     */
    public float getValue() {
        return value;
    }


    /**
     * to search the key if
     * we know the value already.
     * 
     * @param v the float
     * @return the key value
     */
    public int findID(float v) {
        return key;
    }


    /**
     * to return the byte array
     * 
     * @return byte array.
     */
    public byte[] getRecord() {
        return byteArray;
    }


    /**
     * @param o
     *            the record that we compare
     *            with
     * @return we return the integer that will
     *         show if the result is higher or smaller.
     */
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


    /**
     * the string of this class.
     * 
     * @return the string of this class
     */
    public String toString() {
        return this.getKey() + "     " + this.getValue();
    }
}
