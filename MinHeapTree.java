package Project3;
import java.util.ArrayList;

public class MinHeapTree<Record extends Comparable<Record>> {

    private Record[] heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap
    private final int maxSize = 16 * 1024;

    public MinHeapTree() {
        heap = (Record[])new Comparable[maxSize];
        size = maxSize;
        n = 0;
    }


    public Record[] getHeap() {
        return heap;
    }


    public ArrayList<String> print() {
        ArrayList<String> result = new ArrayList<String>();
        if (heapsize() > 0) {
            result.add(this.getMin().toString());
            while (removeMin() == false) {
                result.add(this.getMin().toString());
            }
        }
        return result;
    }


    public int heapsize() {
        return n;
    }


    public void setHeapSize(int s) {
        n = s;
    }


    // Return true if pos a leaf position, false otherwise
    boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    // Return position for left child of pos
    int leftchild(int pos) {
        if (pos >= n / 2) {
            return -1;
        }
        ;
        return 2 * pos + 1;
    }


    // Return position for right child of pos
    int rightchild(int pos) {
        if (pos >= (n - 1) / 2) {
            return -1;
        }
        ;
        return 2 * pos + 2;
    }


    public int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }


    public void swap(int pos1, int pos2) {
        Record temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }


    public void insert(Record key) {
        if (n >= size) {
            return;
        }
        int curr = n++;
        heap[curr] = key; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && (heap[curr].compareTo(heap[parent(curr)]) < 0)) {
            swap(curr, parent(curr));
            curr = parent(curr);
        }
    }


    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }


    public void siftdown(int pos) {
        if ((pos < 0) || (pos >= n)) {
            return; // Illegal position
        }
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (n - 1)) && (heap[j].compareTo(heap[j + 1]) > 0)) {
                j++; // j is now index of child with greater value
            }
            if (heap[pos].compareTo(heap[j]) <= 0) {
                return;
            }
            swap(pos, j);
            pos = j; // Move down
        }
    }


    public boolean removeMin() {
        if (n == 0) {
            return false;
        } // Removing from empty heap

        swap(0, --n); // Swap minimum with last value
        siftdown(0); // Put new heap root val in correct place
        return (n == 0);
    }


    public Record getMin() {
        if (n == 0) {
            return null;
        }
        return heap[0];
    }


    public void setMin(Record min) {
        heap[0] = min;
    }


    public void setHide(int numHidden) {
        for (int i = 0; i < numHidden; i++) {
            insert(heap[(size - numHidden) + i]);
        }
    }
}
