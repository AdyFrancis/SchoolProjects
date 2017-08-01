package edu.ncsu.csc316.ancestrytree.trees;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;
import edu.ncsu.csc316.ancestrytree.io.AhnentafelFileReader;
import edu.ncsu.csc316.ancestrytree.trees.AhnentafelTree;
/**
 * Tests the AhnentafelTree class
 * @author Ady Francis
 *
 */
public class AhnentafelTreeTest {
	
	private String testFile = "input/small-ahnentafel.txt";
	private AhnentafelFileReader afr;

	/**
	 * Tests the constructor, getRoot(), and LevelOrderTraversal
	 */
	@Test
	public void testAhnentafelTree() {
		
		try {
			afr = new AhnentafelFileReader(testFile);
		}
		catch (FileNotFoundException fne) {
			fail();
		}
		
		
		AhnentafelTree tree = new AhnentafelTree(afr.getList());
		
		assertEquals("Billy Smith", tree.getRoot().getName());
		assertEquals("Smith, Billy; Smith, Charles; "
				   + "Jones, Betty; Smith, Henry; "
				   + "Williams, Jane; Jones, William; "
				   + "King, Catherine; Smith, Jimmy; "
				   + "Harper, Joanne; Williams, Richard; "
				   + "Poole, Sally", tree.levelOrderTraversal());
		
		assertEquals("Billy Smith", tree.getChild("Billy Smith").getName());
		assertEquals(1, tree.getChild("Billy Smith").getNum());
		
		assertEquals("Charles Smith", tree.getChild("Charles Smith").getName());
		assertEquals(2, tree.getChild("Charles Smith").getNum());
		
		assertEquals("Betty Jones", tree.getChild("Betty Jones").getName());
		assertEquals(3, tree.getChild("Betty Jones").getNum());
		
		assertEquals("Henry Smith", tree.getChild("Henry Smith").getName());
		assertEquals(4, tree.getChild("Henry Smith").getNum());
		
		assertEquals("Jane Williams", tree.getChild("Jane Williams").getName());
		assertEquals(5, tree.getChild("Jane Williams").getNum());
		
		assertEquals("William Jones", tree.getChild("William Jones").getName());
		assertEquals(6, tree.getChild("William Jones").getNum());
		
		assertEquals("Catherine King", tree.getChild("Catherine King").getName());
		assertEquals(7, tree.getChild("Catherine King").getNum());
	
		assertEquals("Jimmy Smith", tree.getChild("Jimmy Smith").getName());
		assertEquals(8, tree.getChild("Jimmy Smith").getNum());
		
		assertEquals("Joanne Harper", tree.getChild("Joanne Harper").getName());
		assertEquals(9, tree.getChild("Joanne Harper").getNum());
		
		assertEquals("Richard Williams", tree.getChild("Richard Williams").getName());
		assertEquals(10, tree.getChild("Richard Williams").getNum());
		
		assertEquals("Sally Poole", tree.getChild("Sally Poole").getName());
		assertEquals(11, tree.getChild("Sally Poole").getNum());
		
		assertEquals("Billy Smith is Billy Smith", tree.getRelationship("Billy Smith"));
		assertEquals("Charles Smith is Billy Smith's father", tree.getRelationship("Charles Smith"));
		assertEquals("Betty Jones is Billy Smith's mother", tree.getRelationship("Betty Jones"));
		assertEquals("Henry Smith is Billy Smith's grandfather", tree.getRelationship("Henry Smith"));
		assertEquals("Jane Williams is Billy Smith's grandmother", tree.getRelationship("Jane Williams"));
		assertEquals("William Jones is Billy Smith's grandfather", tree.getRelationship("William Jones"));
		assertEquals("Catherine King is Billy Smith's grandmother", tree.getRelationship("Catherine King"));
		assertEquals("Jimmy Smith is Billy Smith's great-grandfather", tree.getRelationship("Jimmy Smith"));
		assertEquals("Joanne Harper is Billy Smith's great-grandmother", tree.getRelationship("Joanne Harper"));
		assertEquals("Richard Williams is Billy Smith's great-grandfather", tree.getRelationship("Richard Williams"));
		assertEquals("Sally Poole is Billy Smith's great-grandmother", tree.getRelationship("Sally Poole"));

	}


}
