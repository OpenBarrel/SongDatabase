//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;
/**
 * A class to represent a Red Black Tree
 * @param <T> the type of data stored in the RBT
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T>{
  
  /**
   * An extended version of the node class tailored for use in a RBT
   * @param <T> the generic type of data stores in the node
   */
  protected static class RBTNode<T> extends Node<T> {
    public boolean isBlack = false;
    public RBTNode(T data) { super(data); }
    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
  }
  
  /**
   * private helper method to get the aunt of a node
   * @param node the node to find the aunt of
   * @return the aunt of the node
   */
  private RBTNode<T> getAunt(RBTNode<T> node){
    RBTNode<T> temp = node.getUp(); // gets the parent of node
    RBTNode<T> grand = temp.getUp(); // gets the grandparent of node
    
    // if the parent is the left child of grand, sets temp to the right child of grand
    if (grand.getDownLeft() == temp)
      temp = grand.getDownRight();
    // if the parent is the right child of grand, sets temp to the left child of grand
    else
      temp = grand.getDownLeft();
    
    // returns temp
    return temp;
  }
  
  /**
   * Used to resolve any RBT property violations after inserting into the RBT
   * @param newNode the new red node that was added
   */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
    // case 0: the parent is black    
    if (newNode == this.root || newNode.getUp().isBlack == true )
      return;
    
    // checks if the aunt is red and not null
    if (getAunt(newNode) != null && getAunt(newNode).isBlack == false) {
    // case 1: the aunt is red
      newNode.getUp().isBlack = true; // sets parent to black
      getAunt(newNode).isBlack = true; // sets aunt to black
      newNode.getUp().getUp().isBlack = false; // sets grandparent to red
      
    // call this method again on grandparent to check for violations
    enforceRBTreePropertiesAfterInsert(newNode.getUp().getUp());
      
    } 
    // if the aunt is node black or null
    else {      
    // case 2: the aunt is black | also include case 2.5: the new node is between parent and grand
    RBTNode<T> grand = newNode.getUp().getUp(); // stores the grandparent
    RBTNode<T> parent = newNode.getUp(); // stores the parent
    
    // checks if child is between parent and grand parent (case 2.5)
    if (grand.data.compareTo(newNode.data) < 0 && newNode.data.compareTo(parent.data) < 0 ||
        grand.data.compareTo(newNode.data) > 0 && newNode.data.compareTo(parent.data) > 0) {
      // rotate newNode and parent
      this.rotate(newNode, newNode.getUp());
      // change the child pointer to point at the new child
      newNode = parent; 
    }
    
    // color swap parent and grandparent
    boolean temp = newNode.getUp().getUp().isBlack; // stores color of grandparent
    newNode.getUp().getUp().isBlack = newNode.getUp().isBlack; // sets grand color to parent color
    newNode.getUp().isBlack = temp; // sets parent color to stores grandparent colors, complete!
    
    // rotate parent and grandparent
    this.rotate(newNode.getUp(), newNode.getUp().getUp());
    }
  }
  
  /**
  * RBT version of the insert method
  * @param data the data to be stored in the new node
  * @return true if the node was successfully added
  * @throws NullPointerExcepton if data is null
  */
  @Override
  public boolean insert(T data) throws NullPointerException {
    // creates and instantiates a new RBT node with the provided data
    RBTNode<T> newNode = new RBTNode<T>(data);
    // calls the insertHelper method to insert the new node as a normal BST node
    insertHelper(newNode);
    // calls method to enforce the RBT properties after the new node was inserted
    enforceRBTreePropertiesAfterInsert(newNode);
    // sets the root node to black
    ((RBTNode<T>)root).isBlack = true;
    
    // returns true if the node was successfully added
    return true; // stub
  }

  
  /**
   * Tests that the RBTInsert works when the parent is red and the aunt is red
   * @return true if all the tests pass
   */
/*
  @Test
  public void TestCase1() {
    // creates a populates a RBT with a black root node and two red children
    RedBlackTree<Integer> tester = new RedBlackTree<Integer>();
    tester.insert(6);
    tester.insert(5);
    tester.insert(8);
    
    // adds another new node, activating the algorithm for red aunts
    tester.insert(9);
    
    // 6, 5, and 8 should be black, 9 should be red
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(6)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(5)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(8)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(9)).isBlack, "Bad color");
    
    // now tests this algorithm when the parent color is not being swapped with the root
    tester.insert(7);
    tester.insert(10);
    
    // 6, 5, 7, and 9 should be black, 8 and 10 should be red
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(6)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(5)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(7)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(9)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(8)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(10)).isBlack, "Bad color");
  }
  */
  /**
   * Tests that the RBTInsert works when the parent is red and the aunt is black (both line and zig)
   * @return true if all the tests pass
   */
/*  @Test
  
public void TestCase2() {
    // creates a populates a RBT with two black nodes and one red node
    RedBlackTree<Integer> tester = new RedBlackTree<Integer>();
    tester.insert(6);
    tester.insert(5);
    tester.insert(8);
    
    // forcefully set the 8 node to black for the sake of testing despite inbalance
    ((RBTNode<Integer>)tester.findNode(8)).isBlack = true;
    
    // tests inserting a node that will have a black aunt(line case)
    tester.insert(4);
    
    // 5 should be rotated and color swapped with 6
    // 5 and 8 should be black, 6 and 4 should be red
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(5)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(8)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(6)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(4)).isBlack, "Bad color");
    
    // verifies that the level order traversal is correct
    Assertions.assertEquals(true, tester.toLevelOrderString().equals("[ 5, 4, 6, 8 ]"), 
        "Tree is incorrect");
    
    // ensures that 5 is now the root
    Assertions.assertEquals(true, tester.findNode(5) == tester.root, "5 is not the root");

    // ensures that the parent of 6 is now 5
    Assertions.assertEquals(true, tester.findNode(6).up == tester.findNode(5), 
        "6 is not child of 5");

    // ensures that the right child of 5 is now 6
    Assertions.assertEquals(true,tester.findNode(5).down[1] == tester.findNode(6), 
        "6 is not the right child of 5");
    
    // ensures that 5's parent reference is now null
    Assertions.assertEquals(true,tester.findNode(5).up == null, "5's up is not null");
      
    // resets the tree
    tester.clear();
    
    // re-populates a RBT with two black nodes and one red node
    tester.insert(6);
    tester.insert(4);
    tester.insert(8);
    
    // forcefully set the 8 node to black for the sake of testing despite inbalance
    ((RBTNode<Integer>)tester.findNode(8)).isBlack = true;
    
    // tests inserting a node that will have a black aunt(zig case)
    tester.insert(5);
    // 5 should be swapped with 4, 5 should be swapped with 6
    // 5 and 8 should be black, 4 and 6 should be red
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(5)).isBlack, "Bad color");
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(8)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(6)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(4)).isBlack, "Bad color");   
    
    // verifies that the level order traversal is correct
    Assertions.assertEquals(true, tester.toLevelOrderString().equals("[ 5, 4, 6, 8 ]"), 
        "RBT is incorrect");
    
    // ensures that 5 is now the root
    Assertions.assertEquals(true, tester.findNode(5) == tester.root, "5 is not the root");

    // ensures that the parent of 6 is now 5
    Assertions.assertEquals(true,tester.findNode(6).up == tester.findNode(5), 
        "5 is not the parent of 6");
    
    // ensures that the right child of 5 is now 6
    Assertions.assertEquals(true,tester.findNode(5).down[1] == tester.findNode(6), 
        "6 is not the right child of 5");

    // ensures that 5's parent reference is now null
    Assertions.assertEquals(true,tester.findNode(5).up == null, "5's up is not null");
  }
  */
  /**
   * Tests that the RBTInsert works when the parent is red and the aunt is null
   * @return true if all the tests pass
   */
/*  @Test
  
public void TestCase3() {
    // creates a populates a RBT with a black node with one red child
    RedBlackTree<Integer> tester = new RedBlackTree<Integer>();
    tester.insert(6);
    tester.insert(7);
    
    // tests inserting another node to the right of 7
    tester.insert(8);
    
    // verifies that 7 is now black and 6 and 8 are red after the rotation
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(7)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(8)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(6)).isBlack, "Bad color");
    
    // verifies that the level order traversal is correct
    Assertions.assertEquals(true,tester.toLevelOrderString().equals("[ 7, 6, 8 ]"), 
        "RBT is incorrect");
    
    // insert node to make 6 and 8 black
    tester.insert(9);
    
    // insert 10, causing 9 and 8 to rotate and swap colors
    tester.insert(10);
    
    // verifies that 9 is now black, and 8 and 10 are red
    Assertions.assertEquals(true,((RBTNode<Integer>)tester.findNode(9)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(8)).isBlack, "Bad color");
    Assertions.assertEquals(false,((RBTNode<Integer>)tester.findNode(10)).isBlack, "Bad color");
    
    // verifies that the level order traversal is correct
    Assertions.assertEquals(true,tester.toLevelOrderString().equals("[ 7, 6, 9, 8, 10 ]"), 
        "RBT is incorrect");
  }
*/  
/**
   * Tester method based off Professor Florian's slides to test with a large number of insertions
   * @return true if all tests pass
   */
/*
  @Test 
public void TestLargeCase() {
    // create a populate large RBT
    RedBlackTree<Integer> tester = new RedBlackTree<Integer>();
    tester.insert(7);
    tester.insert(14);
    tester.insert(18);
    tester.insert(23);
    tester.insert(1);
    tester.insert(11);
    tester.insert(20);
    tester.insert(29);
    tester.insert(25);
    tester.insert(27);
    Assertions.assertEquals(true,tester.toLevelOrderString().equals("[ 20, 14, 25, 7, 18, 23, 29, "
        + "1, 11, 27 ]"), "RBT is incorrect");
  }
	*/
}
