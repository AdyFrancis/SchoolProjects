package edu.ncsu.csc316.ancestrytree.trees;

import static org.junit.Assert.*;


import org.junit.Test;

import edu.ncsu.csc316.ancestrytree.manager.AncestryTreeManager;

/**
 * This class tests TraversalTree
 * @author Ady Francis
 *
 */
public class TraversalTreeTest {

	private String preOrderFilePath = "input/small-preorder.txt";
	private String postOrderFilePath = "input/small-postorder.txt";
	
	/**
	 * Tests the setRoot, getRoot, and getChild method
	 * @author Ady Francis
	 *
	 */
	@Test
	public void test() {
		AncestryTreeManager atm = new AncestryTreeManager(preOrderFilePath, postOrderFilePath);
		TraversalTree tree = (TraversalTree)atm.getTree();
		assertEquals("Leonard, Henry", tree.getRoot().toString());
		assertEquals(null, tree.getChild("Jones, Xena"));
		
	}
}
