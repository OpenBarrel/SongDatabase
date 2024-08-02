import java.util.LinkedList;
import java.util.Stack;


/**
 * Binary Search Tree implementation with a Node inner class for representing the nodes of the tree.
 * We will turn this Binary Search Tree into a self-balancing tree as part of project 1 by modifying
 * its insert functionality. In week 0 of project 1, we will start this process by implementing tree
 * rotations.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

  /**
   * This class represents a node holding a single value within a binary tree.
   */
  protected static class Node<T> {
    public T data;

    // up stores a reference to the node's parent
    public Node<T> up;
    // The down array stores references to the node's children:
    // - down[0] is the left child reference of the node,
    // - down[1] is the right child reference of the node.
    // The @SupressWarning("unchecked") annotation is use to supress an unchecked
    // cast warning. Java only allows us to instantiate arrays without generic
    // type parameters, so we use this cast here to avoid future casts of the
    // node type's data field.
    @SuppressWarnings("unchecked")
    public Node<T>[] down = (Node<T>[]) new Node[2];

    public Node(T data) {
      this.data = data;
    }

    /**
     * @return true when this node has a parent and is the right child of that parent, otherwise
     *         return false
     */
    public boolean isRightChild() {
      return this.up != null && this.up.down[1] == this;
    }

  }

  protected Node<T> root; // reference to root node of tree, null when empty
  protected int size = 0; // the number of values in the tree

  /**
   * Inserts a new data value into the tree. This tree will not hold null references, nor duplicate
   * data values.
   * 
   * @param data to be added into this binary search tree
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided data argument is null
   */
  public boolean insert(T data) throws NullPointerException {
    if (data == null)
      throw new NullPointerException("Cannot insert data value null into the tree.");
    return this.insertHelper(new Node<>(data));
  }

  /**
   * Performs a naive insertion into a binary search tree: adding the new node in a leaf position
   * within the tree. After this insertion, no attempt is made to restructure or balance the tree.
   * 
   * @param node the new node to be inserted
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided node is null
   */
  protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
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
        if (compare == 0) {
          return false;
        } else if (compare < 0) {
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

  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will throw
   * an IllegalArgumentException.
   * 
   * @param child  is the node being rotated from child to parent position (between these two node
   *               arguments)
   * @param parent is the node being rotated from parent to child position (between these two node
   *               arguments)
   * @throws IllegalArgumentException when the provided child and parent node references are not
   *                                  initially (pre-rotation) related that way
   */
  protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
    // check if child is a left or right child of parent; if neither throw exception
    if (child.up != parent || parent == null || child == null)
      throw new IllegalArgumentException("Child is not related to parent");
    // if it is the right child (LEFT ROTATION)
    if (child.isRightChild()) {
      // set parents right child to the child's left child
      parent.down[1] = child.down[0];
      // if the parent's new child is not null, set it's child's parent to itself
      if (parent.down[1] != null)
        parent.down[1].up = parent;
      // set the child's parent to the parent's parent
      child.up = parent.up; 
      // if the parent is the root, set the root to the child
      if (parent.up == null)
        this.root = child;
      // if the parent was a right child of it's parent, set it's parent's right child to the child node
      else if (parent == parent.up.down[1])
        parent.up.down[1] = child;
      else
      // if the parent was a left child of it's parent, set it's parent's left child to the child node
        parent.up.down[0] = child;
      // set the child's left child to the parent
      child.down[0] = parent;
      // set the parent's parent to the child
      parent.up = child;
      
    }
    // if it is the left child (RIGHT ROTATION)
    else {
      // set the parent's left child to the child's left child
      parent.down[0] = child.down[1];
      // if the parent's new left child is not null, set it's porent to parent
      if (parent.down[0] != null)
        parent.down[0].up = parent;
      // set the child's parent to the parent's parent
      child.up = parent.up;
      // if the parent was the root, set the root to the child
      if (parent.up == null)
        this.root = child;
      else if (parent == parent.up.down[1])
      // if the parent was a right child of it's parent, set it's parent's right child to the child node
        parent.up.down[1] = child;
      else
      // if the parent was a left child of it's parent, set it's parent's left child to the child node
        parent.up.down[0] = child;
      // set the child's right child to the parent
      child.down[1] = parent;
      // set the parent's parent to the child
      parent.up = child;
    }
  }

  /**
   * Get the size of the tree (its number of nodes).
   * 
   * @return the number of nodes in the tree
   */
  public int size() {
    return size;
  }

  /**
   * Method to check if the tree is empty (does not contain any node).
   * 
   * @return true of this.size() returns 0, false if this.size() != 0
   */
  public boolean isEmpty() {
    return this.size() == 0;
  }

  /**
   * Checks whether the tree contains the value *data*.
   * 
   * @param data a comparable for the data value to check for
   * @return true if *data* is in the tree, false if it is not in the tree
   */
  public boolean contains(Comparable<T> data) {
    // null references will not be stored within this tree
    if (data == null) {
      throw new NullPointerException("This tree cannot store null references.");
    } else {
      Node<T> nodeWithData = this.findNode(data);
      // return false if the node is null, true otherwise
      return (nodeWithData != null);
    }
  }

  /**
   * Removes all keys from the tree.
   */
  public void clear() {
    this.root = null;
    this.size = 0;
  }

  /**
   * Helper method that will return the node in the tree that contains a specific key. Returns null
   * if there is no node that contains the key.
   * 
   * @param data the data value for which we want to find the node that contains it
   * @return the node that contains the data value or null if there is no such node
   */
  protected Node<T> findNode(Comparable<T> data) {
    Node<T> current = this.root;
    while (current != null) {
      int compare = data.compareTo(current.data);
      if (compare == 0) {
        // we found our value
        return current;
      } else if (compare < 0) {
        if (current.down[0] == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the left subtree
        current = current.down[0];
      } else {
        if (current.down[1] == null) {
          // we have hit a null node and did not find our node
          return null;
        }
        // keep looking in the right subtree
        current = current.down[1];
      }
    }
    return null;
  }

  /**
   * This method performs an inorder traversal of the tree. The string representations of each data
   * value within this tree are assembled into a comma separated string within brackets (similar to
   * many implementations of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
   * 
   * @return string containing the ordered values of this tree (in-order traversal)
   */
  public String toInOrderString() {
    // generate a string of all values of the tree in (ordered) in-order
    // traversal sequence
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    int nodesVisited = 0;
    if (this.root != null) {
      Stack<Node<T>> nodeStack = new Stack<>();
      Node<T> current = this.root;
      while (!nodeStack.isEmpty() || current != null) {
        if (current == null) {
          Node<T> popped = nodeStack.pop();
          if (++nodesVisited > this.size()) {
            throw new RuntimeException(
                "visited more nodes during traversal than there are keys in the tree; make sure there is no loop in the tree structure");
          }
          sb.append(popped.data.toString());
          if (!nodeStack.isEmpty() || popped.down[1] != null)
            sb.append(", ");
          current = popped.down[1];
        } else {
          nodeStack.add(current);
          current = current.down[0];
        }
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  /**
   * This method performs a level order traversal of the tree. The string representations of each
   * data value within this tree are assembled into a comma separated string within brackets
   * (similar to many implementations of java.util.Collection). This method will be helpful as a
   * helper for the debugging and testing of your rotation implementation.
   * 
   * @return string containing the values of this tree in level order
   */
  public String toLevelOrderString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ ");
    int nodesVisited = 0;
    if (this.root != null) {
      LinkedList<Node<T>> q = new LinkedList<>();
      q.add(this.root);
      while (!q.isEmpty()) {
        Node<T> next = q.removeFirst();
        if (++nodesVisited > this.size()) {
          throw new RuntimeException(
              "visited more nodes during traversal than there are keys in the tree; make sure there is no loop in the tree structure");
        }
        if (next.down[0] != null)
          q.add(next.down[0]);
        if (next.down[1] != null)
          q.add(next.down[1]);
        sb.append(next.data.toString());
        if (!q.isEmpty())
          sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

  public String toString() {
    return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
  }

  // Implement at least 3 tests using the methods below. You can
  // use your notes from lecture for ideas of rotation examples to test with.
  // Make sure to include rotations at the root of a tree in your test cases.
  // Give each of the methods a meaningful header comment that describes what is being
  // tested and make sure your tests have inline comments that help with reading your test code.
  // If you'd like to add additional tests, then name those methods similar to the ones given
  // below.
  // Eg: public static boolean test4() {}
  // Do not change the method name or return type of the existing tests.
  // You can run your tests through the static main method of this class.

  /**
   * Will test rotation with a node that has one child
   * 
   * @return true if the test is passed
   */
  public static boolean test1() {
    // creating BST
    BinarySearchTree<Integer> tester = new BinarySearchTree<Integer>();
    tester.insert(4);
    tester.insert(6);
    tester.insert(8);
    int temp = tester.size;

    // rotates the node containing 6 and it's parent node containing 4
    tester.rotate(tester.findNode(6), tester.root);

    // check that the rotation was successful
    if (tester.toLevelOrderString().equals("[ 6, 4, 8 ]") != true)
      return false;
    // verify that size is unchanged
    else if (temp != tester.size)
      return false;
    // verify that the node with 6 is now the left child of the Node with 7
    else if (tester.findNode(6).down[0] != tester.findNode(4))
      return false;

    // returns true if all tests are passed
    return true;
  }

  /**
   * Will test (LEFT)rotation with a root node with two children (at least)
   * 
   * @return true if the test is passed
   */
  public static boolean test2() {
    // create the BST
    BinarySearchTree<Integer> tester = new BinarySearchTree<Integer>();
    tester.insert(4);
    tester.insert(2);
    tester.insert(6);
    tester.insert(5);
    tester.insert(8);
    int temp = tester.size;

    // Rotate the root and it's right child
    tester.rotate(tester.findNode(6), tester.root);

    // tests that the rotate was successful
    if (tester.toLevelOrderString().equals("[ 6, 4, 8, 2, 5 ]") != true)
      return false;
    // verify that size is unchanged
    else if (temp != tester.size)
      return false;
    // verify that root is property changed
    else if (tester.root != tester.findNode(6))
      return false;
    // verify that the node with 4 is now the left child of the Node with 6
    else if (tester.findNode(6).down[0] != tester.findNode(4))
      return false;

    // returns true if all tests are passed
    return true;
  }

  /**
   * Will test (RIGHT)rotation with a node with two children
   * 
   * @return true if the test is passed
   */
  public static boolean test3() {
    // create the BST
    BinarySearchTree<Integer> tester = new BinarySearchTree<Integer>();
    tester.insert(6);
    tester.insert(4);
    tester.insert(8);
    tester.insert(2);
    tester.insert(5);
    int temp = tester.size;

    // rotate nodes 4 and 6
    tester.rotate(tester.findNode(4), tester.findNode(6));

    // tests that the rotate was successful
    if (tester.toLevelOrderString().equals("[ 4, 2, 6, 5, 8 ]") != true)
      return false;
    // verify that size is unchanged
    else if (temp != tester.size)
      return false;
    // verify that root is property changed
    else if (tester.root != tester.findNode(4))
      return false;
    // verify that the node with 6 is now the right child of the Node with 4
    else if (tester.findNode(4).down[1] != tester.findNode(6))
      return false;

    // returns true if all tests are passed
    return true;
  }

  /**
   * Additional Assurance tests for failures
   * 
   * @return true if the tests are passed
   */
  private static boolean test4() {
    // create the BST
    BinarySearchTree<Integer> tester = new BinarySearchTree<Integer>();
    tester.insert(6);
    // verifies that rotate will throw an exception with called with only one node
    try {
      tester.rotate(tester.root, tester.root);
      return false;
    } catch (IllegalArgumentException e) {
      // an exception was thrown
    }

    // verifies that rotate will throw an exception when one of the inputs is null
    try {
      tester.rotate(tester.root, tester.root.down[0]);
      return false;
    } catch (IllegalArgumentException e) {
      // an exception was thrown
    }
    // creating the remaining nodes in BST
    tester.insert(4);
    tester.insert(8);
    tester.insert(2);
    tester.insert(5);

    // verifies that rotating the same two nodes twice gets the same BST back
    String original = tester.toInOrderString();
    tester.rotate(tester.findNode(4), tester.findNode(6));
    tester.rotate(tester.findNode(6), tester.findNode(4));

    // verifying the BST is back to it's orignal state...
    if (!(tester.toInOrderString().equals(original)))
      return false;

    // verifies that rotate will not work when two nodes are not directly related
    try {
      tester.rotate(tester.findNode(2), tester.root);

      return false;
    } catch (IllegalArgumentException e) {
      // exception was thrown
    }

    // verifies that rotate will not work when two nodes are related, but input in the wrong order
    try {
      tester.rotate(tester.findNode(6), tester.findNode(4));
      System.out.println("SHIT");
      return false;
    } catch (IllegalArgumentException e) {
      // exception was thrown
    }

    // returns true if all tests pass
    return true;
  }

  /**
   * Will test performing multiple rotations on one tree
   * 
   * @return true if the tests pass
   */
  private static boolean test5() {
    // create the BST
    BinarySearchTree<Integer> tester = new BinarySearchTree<Integer>();
    tester.insert(6);
    tester.insert(4);
    tester.insert(8);
    tester.insert(2);
    tester.insert(5);
    int temp = tester.size;

    // performs rotations
    tester.rotate(tester.findNode(5), tester.findNode(4));
    tester.rotate(tester.findNode(2), tester.findNode(4));
    tester.rotate(tester.findNode(8), tester.findNode(6));

    // verifies that the BST level order is correct
    if (tester.toLevelOrderString().equals("[ 8, 6, 5, 2, 4 ]") != true)
      return false;
    // verify that size is unchanged
    else if (temp != tester.size)
      return false;
    // verify that root is property changed
    else if (tester.root != tester.findNode(8))
      return false;
    // verify that the node with 6 is now the left child of the Node with 8
    else if (tester.findNode(8).down[0] != tester.findNode(6))
      return false;
    // verifies that the left child of the 5 node is 2
    else if (tester.findNode(5).down[0] != tester.findNode(2))
      return false;
    // verifies that the right child of the 2 node is now 4
    else if (tester.findNode(2).down[1] != tester.findNode(4))
      return false;

    // returns true if all tests pass
    return true;
  }
  
  private static boolean test6() {
    // creates a populates a BST
    BinarySearchTree<Integer> tester = new BinarySearchTree<Integer>();
    tester.insert(6);
    tester.insert(4);
    tester.insert(8);
    tester.insert(2);
    tester.insert(5);
    tester.insert(7);
    tester.insert(10);
    tester.insert(11);
    
    // rotates two nodes that are neither the root or a leaf
    tester.rotate(tester.findNode(10), tester.findNode(8));
    
    // verifies that the BST was properly altered
    if (tester.toLevelOrderString().equals("[ 6, 4, 10, 2, 5, 8, 11, 7 ]") != true)
      return false;
    
    // return true if the test is passed
    return true;
  }
  /**
   * Main method to run tests. If you'd like to add additional test methods, add a line for each of
   * them.
   * 
   * @param args
   */
  /*
  public static void main(String[] args) {
    System.out.println("Test 1 passed: " + test1());
    System.out.println("Test 2 passed: " + test2());
    System.out.println("Test 3 passed: " + test3());
    System.out.println("Test 4 passed: " + test4());
    System.out.println("Test 5 passed: " + test5());
    System.out.println("Test 6 passed: " + test6());
  }
  */

}
