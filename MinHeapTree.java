
import java.util.ArrayList;
/**
 * MinHeap tree that the root of the tree
 * has the smallest value. and the maximum size
 * of the tree is 16 blocks of records.
 * @author youwei chen and honghao zhang
 *
 * @param <Record> record to store.
 */
@SuppressWarnings("hiding")
public class MinHeapTree<Record extends Comparable<Record>> {

    private Record[] heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap
    private final int maxSize = 16 * 1024;

    /**
     * MinHeap tree constructor.
     */
    @SuppressWarnings("unchecked")
    public MinHeapTree() {
        heap = (Record[])new Comparable[maxSize];
        size = maxSize;
        n = 0;
    }

    /**
     * to get the record array
     * @return the array
     */
    public Record[] getHeap() {
        return heap;
    }

    /**
     * to print the value of record from 
     * smallest to highest in the tree.
     * @return list that each index stores the 
     * value from smallest to largest.
     */
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

    /**
     * return the size of heap
     * @return the size of heap.
     */
    public int heapsize() {
        return n;
    }

    /**
     * to set the heap size
     * @param s the size that we
     * want to set.
     */
    public void setHeapSize(int s) {
        n = s;
    }


    /**
     * to return if the position is leaf.
     * @param pos position
     * @return true or false.
     */
    boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    /**
     * to return the left-child position
     * @param pos position
     * @return the left-child position
     */
    int leftchild(int pos) {
        if (pos >= n / 2) {
            return -1;
        }
        ;
        return 2 * pos + 1;
    }


    /**
     * Return position for right child of POS
     * @param pos POS
     * @return integer position
     */
    int rightchild(int pos) {
        if (pos >= (n - 1) / 2) {
            return -1;
        }
        ;
        return 2 * pos + 2;
    }

    /**
     * to return its parent position
     * @param pos position 
     * @return integer position.
     */
    public int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }


    /**
     * to swap the value in two 
     * different positions
     * @param pos1 position1
     * @param pos2 position2
     */
    public void swap(int pos1, int pos2) {
        Record temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }

    /**
     * to insert the record into 
     * the tree.
     * @param key key
     */
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

    /**
     * to build the heap
     */
    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }


    /**
     * to shift it down
     * @param pos position
     */
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

    /**
     * to remove the minimum value
     * @return true or false.
     */
    public boolean removeMin() {
        if (n == 0) {
            return false;
        } // Removing from empty heap

        swap(0, --n); // Swap minimum with last value
        siftdown(0); // Put new heap root val in correct place
        return (n == 0);
    }

    /**
     * the get the minimum value of the tree.
     * @return record 
     */
    public Record getMin() {
        if (n == 0) {
            return null;
        }
        return heap[0];
    }

    /**
     * to set the minimum value.
     * @param min minimum value.
     */
    public void setMin(Record min) {
        heap[0] = min;
    }

    /**
     * to setHide.
     * @param numHidden numHidden
     */
    public void setHide(int numHidden) {
        for (int i = 0; i < numHidden; i++) {
            insert(heap[(size - numHidden) + i]);
        }
    }
}
