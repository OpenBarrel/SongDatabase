import java.util.Iterator;
//import org.junit.jupiter.api.Test;
//import BinarySearchTree.Node;
//import org.junit.jupiter.api.Assertions;
import java.util.Stack;
import java.util.NoSuchElementException;

public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> {

    // stores starting point (uses anonymous class to define a version to compareTo that 
    // always returns 1
    Comparable<T> startPoint = new Comparable<T>() {
      @Override
      public int compareTo(T o) {
        return -1; // SKEPTICAL OF THIS, Doesn't this mean that THIS will always be BIGGER than THAT
      }
    };
  
    /**
     * When a non-null start point is set, the first value returned by this 
     * iterator will be the smallest value in the collection that is smaller than 
     * or equal to the specified start point.  When no (or a null) start point is 
     * set, the iterator will step through all values in the collection.
     */
    public void setIterationStartPoint(Comparable<T> start) {
      // resets the start point if the input is null
      if (start == null) {
        this.startPoint = new Comparable<T>() {
          @Override
          public int compareTo(T o) {
            return -1;  // THESE STARTING VALUES WERE CHANGED
          }    
        };
      } else {
        // if it is not null, sets the new start point
        this.startPoint = start;
      }  
    }
    
    /**
     * Creates a new iterator object
     * 
     * @return a new RBTIterator
     */
    public Iterator<T> iterator() { return new RBTIterator<T>(this.root,startPoint); }

    /**
     * Contains the methods for an RBTIterator object
     * 
     * @param <R> the Type of object stored in the tree the RBTIterator is for
     */
    private static class RBTIterator<R> implements Iterator<R> {
      private Comparable<R> startPoint; // starting point for the iterator
      private Stack<Node<R>> stack; // empty stack of Node<R>
      
    /**
     * Constructor for an RBTIterator
     * @param root the root of the tree 
     * @param startPoint the start point for the iterator
     */
	public RBTIterator(Node<R> root, Comparable<R> startPoint) {
	  this.startPoint = startPoint; // sets the startPoint to the input  
	  this.stack = new Stack<Node<R>>(); // creates an empty stack
	  buildStackHelper(root); // calls the buildStackHelper on the empty stack with the root input
	}

	/**
	 * Helper method to construct the initial stack 
	 * @param node the root of the tree
	 */
	private void buildStackHelper(Node<R> node) {	  
	  // base case 
	  if (node == null)
	    return;
	  
	  // recursive cases (2)
	  
	  // when data in the node is smaller than the start value, go to right subtree
	  if (startPoint.compareTo(node.data) > 0) {
	    buildStackHelper(node.down[1]); // recursively call on right subtree
	  // when data is greater than or equal to the start point
	  } else if (startPoint.compareTo(node.data) <= 0) {
	    stack.add(node); // pushes the node onto the stack
	    buildStackHelper(node.down[0]); // moves onto the left subtree (smaller values)
	  } 
	}
	/*
	Implement the next() and hasNext() methods of the RBTIterator class.  The stack built by the 
	method above should always contain the node with the next value to iterate through, and ONLY the
	 ancestors of that node that contain values larger than or equal to that node's data.  As you 
	 step through the values in the tree, you will sometimes need to build this stack to contain 
	 more values, and the buildStackHelper method will be convenient to re-use for this purpose.  
	 If next() is called with an empty stack, the method should throw a NoSuchElementException.
	*/
	
	public boolean hasNext() { 
	  // if the stack is empty, then there are no more nodes
	  return !stack.isEmpty(); 
	  }

	public R next() { 
	  // throw an exception if the stack is empty
	  if (stack.isEmpty())
	    throw new NoSuchElementException("empty stack");
	  
	  // Store the next 
	  Node<R> toReturn = stack.pop();
	  
	  // build the stack with the right children (if it is there)
	  if (toReturn.down[1] != null)
        buildStackHelper(toReturn.down[1]);
	  
	  // return the data of the node remove from the top of the stack
	  return toReturn.data; 
	  }
    }
    
    /**
     * Performs a naive insertion into a binary search tree: adding the new node in a leaf position
     * within the tree. After this insertion, no attempt is made to restructure or balance the tree.
     * 
     * @param node the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    @Override
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException{
      
      if (newNode == null)
        throw new NullPointerException("new node cannot be null");

      if (this.root == null) {
        // add first node to an empty tree
        root = newNode;
        size++;
        return true;
      } else {
        // insert into subtree
        Node<T> current = this.root;

        while (true) {
          
          int compare = newNode.data.compareTo(current.data);
          // EDITS TO ALLOW FOR DUPLICATES
          // if (compare == 0) {
          //  return false;
          //}  
          if (compare <= 0) { // dupes are not moving left?
            // insert in left subtree
            if (current.down[0] == null) {
              // empty space to insert into
              current.down[0] = newNode;
              newNode.up = current;
              this.size++;
              return true;
            } else {
              // no empty space, keep moving down the tree
              current = current.down[0];
            }
          } else {
            // insert in right subtree
            if (current.down[1] == null) {
              // empty space to insert into
              current.down[1] = newNode;
              newNode.up = current;
              this.size++;
              return true;
            } else {
              // no empty space, keep moving down the tree
              current = current.down[1];
              
            }
          }
        }
      }
    }
    
    // Test Methods
    
    /**
     * Tests the Iterator with trees containing different kinds of Comparable data: 
     * Integers vs Strings
     */
 /*   @Test
    public void Test1() {
      // creates a populate a RBT
      IterableRedBlackTree<Integer> tester = new IterableRedBlackTree<Integer>();
      
      // for my sake for testing
      for (int i = 1; i < 10; i++)
        tester.insert(i);
      
      int prev = 0;

      for (int x:tester) {
        // Checks that previous is less than the cur
        Assertions.assertEquals(true, x > prev, "not in sorted order");
        prev = x;
      }
      
      // creates and populates a new tester RBT for type String
      IterableRedBlackTree<Character> tester2 = new IterableRedBlackTree<Character>();
      tester2.insert('b');
      tester2.insert('c');
      tester2.insert('d');
      tester2.insert('e');
      tester2.insert('f');
      tester2.insert('g');
      tester2.insert('h');
      tester2.insert('i');
      
      Character prior = 'a';
      for (char c:tester2) {
        // Checks that previous is less than the cur
        Assertions.assertEquals(true, prior.compareTo(c) < 0, "not in sorted order");
        prior = c;
      }
   
 } */
    /**
     * Tests the Iterator with trees with duplicates
     */
   /* @Test
    public void Test2() {
      // creates a populate a RBT
      IterableRedBlackTree<Integer> tester = new IterableRedBlackTree<Integer>();
      for (int i = 1; i < 10; i++)
        tester.insert(i);
      
      int prev = 0;
      // tests a tree with no duplicates
      for (int x:tester) {
        // Checks that previous is less than the current
        Assertions.assertEquals(true, x >= prev, "not in sorted order");
        prev = x;
      }
      // adds a duplicate value
      tester.insert(5); 
      tester.insert(3);

      String toCompare = "";
      for (int x:tester) {  
        toCompare += x + ", ";
      }
      // verifies that the iterator goes through as expected
      Assertions.assertEquals("1, 2, 3, 3, 4, 5, 5, 6, 7, 8, 9, ", toCompare, "bad list"); 
    }*/
    /**
     * Tests the Iterator when a specific starting point has been set
     */
   /* @Test
    public void Test3() {
      // creates a populate a RBT
      IterableRedBlackTree<Integer> tester = new IterableRedBlackTree<Integer>();
      for (int i = 1; i < 10; i++)
        tester.insert(i);
      
      // Test with no set starting point
      int prev = 0;
      // tests a tree with no duplicates
      for (int x:tester) {
        // Checks that previous is less than the current
        Assertions.assertEquals(true, x >= prev, "not in sorted order");
        prev = x;
      }
      tester.setIterationStartPoint(5);
      
      // tests with a starting point (VERIFY THIS ACTUALLY WORKS!!!) For each loops may work different
      prev = 4;
      // tests a tree with no duplicates
      for (int x:tester) {
        // Checks that previous is less than the current
        Assertions.assertEquals(true, x >= prev, "not in sorted order");
        System.out.println(x);
        prev = x;
      }   
    } 
*/
}
