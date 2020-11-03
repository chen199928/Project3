package Project3;


import student.TestCase;

/**
 * test class
 * 
 * @author honghaozhang073 and adam chen
 * @version 10/24/2020
 */
public class MinHeapTest extends TestCase {
    private MinHeapTree tree;
    private Record record;

    /**
     * setup
     */
    public void setUp() {
        tree = new MinHeapTree(new Record[16*1024], 0, 16*1024);
        
        tree.insert(new Record(1, 1.2f));
        tree.insert(new Record(2, 3f));
        tree.insert(new Record(3, 2f));
        tree.insert(new Record(4, 0.5f));
        tree.insert(new Record(5, 4f));
        tree.insert(new Record(6, 5f));

    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMain() {
        //tree.print();
        
        for(int i = 0 ; i < tree.getSize(); i ++) {
            System.out.println("key: " + tree.getHeap()[i].getValue());
        }
    }
}
