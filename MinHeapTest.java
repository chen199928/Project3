<<<<<<< HEAD
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

    /**
     * setup
     */
    public void setUp() {
        tree = new MinHeapTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(3);
    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMain() {
        tree.print();
    }
}
=======

import student.TestCase;

/**
 * test class
 * 
 * @author honghaozhang073 and adam chen
 * @version 10/24/2020
 */
public class MinHeapTest extends TestCase {
    private MinHeapTree tree;

    /**
     * setup
     */
    public void setUp() {
        tree = new MinHeapTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(3);
    }


    /**
     * test main 1
     * 
     * @throws IOException
     *             throw
     */
    public void testMain() {
        tree.print();
    }
}
>>>>>>> faf6b261618c448aef3ab8b62e8d06db1e7ab6c6
