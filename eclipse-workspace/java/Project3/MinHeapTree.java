


/**
 * 
 * @author youweichen and Honghao Zhang
 * This is the heap class.
 *
 */
public class MinHeapTree<T extends Comparable<T>> {
    //private float[] Heap; 
    private int size; 
    private int maxsize; 
    private RecordCollection<T> Heap;
    

    private static final int FRONT = 1; 
    private static final int MAXSIZE = 16;
    private static final int ARRAYSIZE = 8;
    
    public MinHeapTree() 
    { 
        this.maxsize = MAXSIZE; 
        
        this.size = 0; 
        Heap = new RecordCollection<T>(MAXSIZE+1);
        Heap.set(0, null);
        //Heap[0] = Integer.MIN_VALUE; 
    } 
  
    // Function to return the position of 
    // the parent for the node currently 
    // at pos 
    private int parent(int pos) 
    { 
        return pos / 2; 
    } 
  
    // Function to return the position of the 
    // left child for the node currently at pos 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
  
    // Function to return the position of 
    // the right child for the node currently 
    // at pos 
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 
  
    // Function that returns true if the passed 
    // node is a leaf node 
    private boolean isLeaf(int pos) 
    { 
        if (pos >= (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
  
    // Function to swap two nodes of the heap 
    private void swap(int fpos, int spos) 
    { 
        T tmp; 
        tmp = Heap.get(fpos); 
        Heap.set(fpos, Heap.get(spos));
        Heap.set(spos, tmp);
    } 
  
    // Function to heapify the node at pos 
    private void minHeapify(int pos) 
    { 
  
        // If the node is a non-leaf node and greater 
        // than any of its child 
        if (!isLeaf(pos)) { 
            if (Heap.get(pos).compareTo(Heap.get(leftChild(pos))) > 0 
                ||Heap.get(pos).compareTo(Heap.get(rightChild(pos))) > 0) { 
  
                // Swap with the left child and heapify 
                // the left child 
                if (Heap.get(leftChild(pos)).compareTo(Heap.get(rightChild(pos))) < 0) { 
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
    public void insert(T element) 
    { 
        if (Heap.getIndex() >= maxsize) { 
            return; 
        } 
        Heap.insert(element); 
        size++;
        int current = size; 
  
        while (Heap.get(current).compareTo(Heap.get(parent(current))) < 0) { 
            swap(current, parent(current)); 
            current = parent(current); 
        } 
    } 
  
    // Function to build the min heap using 
    // the minHeapify 
    public void minHeap() 
    { 
        for (int pos = (size / 2); pos >= 1; pos--) { 
            minHeapify(pos); 
        } 
    } 
  
    // Function to remove and return the minimum 
    // element from the heap 
    public T remove() 
    { 
        T popped = Heap.get(FRONT);
        Heap.set(FRONT,Heap.get(size--));
      
        minHeapify(FRONT); 
        return popped; 
    } 
    
 // Function to print the contents of the heap
    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + Heap.get(i) + " LEFT CHILD : " + Heap.get(2*i) + " RIGHT CHILD :" + Heap.get(2*i+1));
            System.out.println();
        }
    }
}
