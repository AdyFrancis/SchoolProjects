package edu.ncsu.csc316.ancestrytree.data;

import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;

/**
 * This class represents the node of the Traversal Tree
 * @author Ady Francis
 *
 */
public class Node {
	private Person p;
	private MultiPurposeList<Node> children;
	@SuppressWarnings("unused")
	private Node parent;
	
	/**
	 * Parameterized constructor for the Node class
	 * @param person the person data type stored in the node
	 */
	public Node (Person person){ 
		p = person;
		children = new MultiPurposeList<Node>();
	}
	
	/**
	 * Returns whether or not the Node has children	
	 * @return true if the children list is not empty
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	/**
	 * Sets the parent to a child
	 * @param parent the parent of the child node
	 */
	private void setParent(Node parent) {
		this.parent = parent;
	}
	/**
	 * Adds a child to the children list 
	 * @param n the child to add
	 */
	public void addChild(Node n) {
		n.setParent(this);
		children.add(n);
	}
	/**
	 * Returns the person data type assigned to the node
	 * @return person data type
	 */
	public Person getData() {
		return p;
	}
	
	/**
	 * Returns the list of children
	 * @return list of children
	 */
	public MultiPurposeList<Node> getChildren() {
		return children;
	}
	
	/**
	 * Returns string representation of the data type
	 * @return
	 */
	@Override
	public String toString() {
		return p.toString();
	}


}
