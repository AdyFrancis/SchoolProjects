package edu.ncsu.csc316.ancestrytree.manager;

import static org.junit.Assert.*;



import org.junit.Test;

/**
 * This tests the AncestryTreeManager class
 * @author Ady Francis
 *
 */
public class AncestryTreeManagerTest {
	
	private String ahnName = "input/small-ahnentafel.txt";
	private String preName = "input/small-preorder.txt";
	private String postName = "input/small-postorder.txt";
	private AncestryTreeManager atm;

	/**
	 * Tests constructing a traversal tree, getRelationship and 
	 * getLevelOrder
	 */
	@Test
	public void testTraversalTreeManager() {
		atm = new AncestryTreeManager(preName, postName);

		assertEquals("LevelOrder[Leonard, Henry; Smith, Teresa;"
				+ " Jones, Xena; Doe, Zachary;"
				+ " Murray, Claire; Williams, Richard; Perry, Percy; ]", atm.getLevelOrder());
		assertEquals("", atm.getRelationship("nameA", "nameB"));
	}
	
	/**
	 * Tests constructing a ahnentafel tree, getRelationship and 
	 * getLevelOrder
	 */
	@Test
	public void testAhnentafelManager() {
		AncestryTreeManager ahn = new AncestryTreeManager(ahnName);
		
		assertEquals("LevelOrder[Smith, Billy; Smith, Charles; "
				   + "Jones, Betty; Smith, Henry; "
				   + "Williams, Jane; Jones, William; "
				   + "King, Catherine; Smith, Jimmy; "
				   + "Harper, Joanne; Williams, Richard; "
				   + "Poole, Sally]", ahn.getLevelOrder());
	
		assertEquals("Billy Smith is Billy Smith", ahn.getRelationship("Billy Smith"));
		assertEquals("Charles Smith is Billy Smith's father", ahn.getRelationship("Charles Smith"));
		assertEquals("Betty Jones is Billy Smith's mother", ahn.getRelationship("Betty Jones"));
		assertEquals("Henry Smith is Billy Smith's grandfather", ahn.getRelationship("Henry Smith"));
		assertEquals("Jane Williams is Billy Smith's grandmother", ahn.getRelationship("Jane Williams"));
		assertEquals("William Jones is Billy Smith's grandfather", ahn.getRelationship("William Jones"));
		assertEquals("Catherine King is Billy Smith's grandmother", ahn.getRelationship("Catherine King"));
		assertEquals("Jimmy Smith is Billy Smith's great-grandfather", ahn.getRelationship("Jimmy Smith"));
		assertEquals("Joanne Harper is Billy Smith's great-grandmother", ahn.getRelationship("Joanne Harper"));
		assertEquals("Richard Williams is Billy Smith's great-grandfather", ahn.getRelationship("Richard Williams"));
		assertEquals("Sally Poole is Billy Smith's great-grandmother", ahn.getRelationship("Sally Poole"));
		
	}
	
}
