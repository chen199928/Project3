package Project3;


public class Record {
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
    
    public int findID(float value) {
       return key; 
    }
}
