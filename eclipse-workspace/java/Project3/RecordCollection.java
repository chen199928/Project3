import java.util.ArrayList;


public class RecordCollection<T extends Comparable<T>>{
    private int index;
    private int max;
    ArrayList<T> list;
    public RecordCollection(int maxSize) {
        max = maxSize;
        index = 0;
        list = new ArrayList<T>();
    }
    
    public void insert(T input) {
        if (!isFull()) {
            list.add(input);
            index++;
        }
    }
    
    public T get(int pos) {
        return list.get(pos);
    }
    
    public int getIndex() {
        return index;
    }
    
    public void set(int pos, T element) {
        list.set(pos, element);
    }
    
    public boolean isFull() {
        return index == max;
    }
}
