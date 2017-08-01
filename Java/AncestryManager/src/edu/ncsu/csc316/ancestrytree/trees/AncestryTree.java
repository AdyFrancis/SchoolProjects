package edu.ncsu.csc316.ancestrytree.trees;

/**
 * Interface for AhnentafelTree and TraversalTree class
 * @author Ady Francis
 *
 */
public interface AncestryTree {

	/**
	 * Returns the string representation 
	 * of the level order traversal of the tree.
	 * @return string representation of the tree
	 */
	public String levelOrderTraversal();
	
	/**
	 * Returns the root of the tree
	 * @return the root of the tree
	 */
	public Object getRoot();
	
	/**
	 * Returns a child node with the specified name
	 * @param name assigned to the child node
	 * @return Person or AhnentafelEntry with the specified name
	 */
	public Object getChild(String name);
}
