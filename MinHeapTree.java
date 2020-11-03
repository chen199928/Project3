package Project3;

public class MinHeapTree {
    private Record[] Heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;
    private static final int MAXSIZE = 16 * 1024 + 1;
    private static final int ARRAYSIZE = 16 * 1024 + 1;

    public MinHeapTree() {
        this.maxsize = MAXSIZE;
        this.size = 0;
        Heap = new Record[ARRAYSIZE];
        Heap[0] = new Record(0, 0);
    }


    // Function to return the position of
    // the parent for the node currently
    // at pos
    private int parent(int pos) {
        return pos / 2;
    }
    
    public int getSize() {
        return size;
    }

    // Function to return the position of the
    // left child for the node currently at pos
    private int leftChild(int pos) {
        return (2 * pos);
    }


    // Function to return the position of
    // the right child for the node currently
    // at pos
    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }


    // Function that returns true if the passed
    // node is a leaf node
    private boolean isLeaf(int pos) {
        if (pos >= (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }


    // Function to swap two nodes of the heap
    private void swap(int fpos, int spos) {
        Record tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }


    // Function to heapify the node at pos
    private void minHeapify(int pos) {

        // If the node is a non-leaf node and greater
        // than any of its child
        if (!isLeaf(pos)) {
            if (Heap[pos].getValue() > Heap[leftChild(pos)].getValue()
                || Heap[pos].getValue() > Heap[rightChild(pos)].getValue()) {

                // Swap with the left child and heapify
                // the left child
                if (Heap[leftChild(pos)].getValue() < Heap[rightChild(pos)]
                    .getValue()) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }

                // Swap with the right child and heapify
                // the right child
                else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }


    // Function to insert a node into the heap
    public void insert(Record element) {
        if (size >= maxsize) {
            return;
        }
        //System.out.println(element.getKey());;
        Heap[++size] = element;

        int current = size;

        while (Heap[current].getValue() < Heap[parent(current)].getValue()) {
            swap(current, parent(current));
            current = parent(current);
        }
    }


    // Function to build the min heap using
    // the minHeapify
    public void minHeap() {
        for (int pos = (size / 2); pos >= 1; pos--) {
            minHeapify(pos);
        }
    }


    // Function to remove and return the minimum
    // element from the heap
    public float remove() {
        float popped = Heap[FRONT].getValue();
        Heap[FRONT] = Heap[size--];
        minHeapify(FRONT);
        return popped;
    }
    
    public Record[] getHeap() {
        return Heap;
    }


    // Function to print the contents of the heap
    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            if (Heap[i] != null) {
                System.out.print(" PARENT : " + Heap[i].getValue());
            }
            if (Heap[2 * i] != null) {
                System.out.print(" LEFT CHILD : " + Heap[2 * i].getValue());
            }
            if (Heap[2 * i + 1] != null) {

                System.out.print(" RIGHT CHILD :" + Heap[2 * i + 1].getValue());
            }
            System.out.println();
        }
    }
}
