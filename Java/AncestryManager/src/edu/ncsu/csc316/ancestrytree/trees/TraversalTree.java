package edu.ncsu.csc316.ancestrytree.trees;

import edu.ncsu.csc316.ancestrytree.data.Node;
import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;
import edu.ncsu.csc316.ancestrytree.list.Queue;

/**
 * This class will be used to store and traverse non-ahnentafel
 * input files. 
 * @author Ady Francis
 *
 */
public class TraversalTree implements AncestryTree {


/** Root node of the tree */
private Node root;

/**
 * Sets the root of the traversal tree
 * @param root the node that will be set as root
 */
public void setRoot(Node root) {
	this.root = root;
}

/**
 * This method returns the root
 * @return the root of the tree
 */
public Node getRoot() {
	return root;
}



/**
 * Returns a description of the relationship between
 * two individuals in the tree, formatted as:
 * [nameA] is [nameB]'s [relationship]
 * @param nameA the first person to find in the tree
 * @param nameB the second person to find in the tree
 * @return a description of how the two people are related
 */
public String getRelationship(String nameA, String nameB) {
    if (nameA.equalsIgnoreCase(nameB)){
    	return nameA + " is " + nameB;
    }
    
    return "";
}
/**
 * Finds and returns a child in the tree given a person
 * @param name the name of the person to find
 * @return a person if found in the tree or null if not found.
 */
public Node getChild(String name) {
	
	if (root == null)
		return null;
	
	Queue<Node> q = new Queue<Node>();
	
	int numNodes = 0;
	
	q.enqueue(root);
	
	while (!q.isEmpty()) {
		numNodes = q.size();
		
		while (numNodes > 0) {
			Node n = q.dequeue();
			if (n.getData().getName().equals(name))
				return n;
			
			if (n.hasChildren()) {
				MultiPurposeList<Node> children = n.getChildren();
				for (int i = 0; i < children.size(); i++) {
					q.enqueue(children.get(i));
				}
			}
			numNodes--;
		}
	}
	return null;
}

/**
 * This method returns the string representation
 * of the level order traversal of the traversal tree.
 * @return string representation of level order traversal
 */
public String levelOrderTraversal() {
	if (root == null)
		return "Empty.";
	
	Queue<Node> q = new Queue<Node>();
	StringBuffer str = new StringBuffer();
	
	int numNodes = 0;
	
	q.enqueue(root);
	
	while (!q.isEmpty()) {
		numNodes = q.size();
		
		while (numNodes > 0) {
			Node n = q.dequeue();
			str.append(n.toString() + "; ");
			
			if (n.hasChildren()) {
				MultiPurposeList<Node> children = n.getChildren();
				for (int i = 0; i < children.size(); i++)
					q.enqueue(children.get(i));
			}
			numNodes--;
		}
	}
	return str.toString();
}


}
