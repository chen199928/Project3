

public class Record implements  Comparable<Record> {
    int key;
    float value;
    
    public Record(int key, float value) {
        this.key = key;
        this.value = value;
    }
    
    public int getKey() {
        return key;
    }
    
    public float getValue() {
        return value;
    }

    @Override
    public int compareTo(Record o) {
        // TODO Auto-generated method stub
        if (this.getKey() > o.getKey()) {
            return 1;
        }
        else if (this.getKey() < o.getKey()) {
            return -1;
        }
        else {
            return 0;
        }

    }
}
