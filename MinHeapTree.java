package Project3;

public class MinHeapTree {
    private Record[] Heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap

    // Constructor supporting preloading of heap contents
    public MinHeapTree(Record[] h, int num, int max) {
        Heap = h;
        n = num;
        size = max;
        buildheap();
    }


    // Return current size of the heap
    public int heapsize() {
        return n;
    }


    // Return true if pos a leaf position, false otherwise
    public boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    // Return position for left child of pos
    public int leftchild(int pos) {
        if (pos >= n / 2)
            return -1;
        return 2 * pos + 1;
    }


    // Return position for right child of pos
    public int rightchild(int pos) {
        if (pos >= (n - 1) / 2)
            return -1;
        return 2 * pos + 2;
    }


    // Return position for parent
    public int parent(int pos) {
        if (pos <= 0)
            return -1;
        return (pos - 1) / 2;
    }


    // Insert val into heap
    public void insert(Record key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        int curr = n++;
        Heap[curr] = key; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && (Heap[curr].getValue() < Heap[parent(curr)].getValue())) {
            swap( curr, parent(curr));
            curr = parent(curr);
        }
    }


    // Heapify contents of Heap
    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--)
            siftdown(i);
    }


    // Put element in its correct place
    public void siftdown(int pos) {
        if ((pos < 0) || (pos >= n))
            return; // Illegal position
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (n - 1)) && (Heap[j].getValue() > Heap[j + 1].getValue()))
                j++; // j is now index of child with greater value
            if (Heap[pos].getValue() <= Heap[j].getValue())
            {
                return;
            }
            swap( pos, j);
            pos = j; // Move down
        }
    }


    // Remove and return minimum value
    public Record removemin() {
        if (n == 0)
            return null; // Removing from empty heap
        swap( 0, --n); // Swap maximum with last value
        if (n != 0) // Not on last element
            siftdown(0); // Put new heap root val in correct place
        return Heap[n];
    }


    public void swap( int fpos, int spos) {
        Record tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
    // Remove and return element at specified position
    public Record remove(int pos) {
        if ((pos < 0) || (pos >= n))
            return null; // Illegal heap position
        if (pos == (n - 1))
            n--; // Last element, no work to be done
        else {
            swap( pos, --n); // Swap with last value
            // If we just swapped in a big value, push it up
            while ((pos > 0) && (Heap[pos].getValue() < Heap[parent(pos)].getValue())) {
                swap( pos, parent(pos));
                pos = parent(pos);
            }
            if (n != 0)
                siftdown(pos); // If it is little, push down
        }
        return Heap[n];
    }
    
    
    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            if (Heap[i] != null) {
                System.out.print(" PARENT : " + Heap[i].getValue());
            }
            if (Heap[2 * i + 1] != null) {
                System.out.print(" LEFT CHILD : " + Heap[2 * i  + 1].getValue());
            }
            if (Heap[2 * i + 2] != null) {

                System.out.print(" RIGHT CHILD :" + Heap[2 * i + 2].getValue());
            }
            System.out.println();
        }
    }
    public Record[] getHeap() {
        return Heap;
    }
    public int getSize() {
        return n;
    }
}
