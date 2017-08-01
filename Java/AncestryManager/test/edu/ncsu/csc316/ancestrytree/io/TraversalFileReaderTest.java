package edu.ncsu.csc316.ancestrytree.io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc316.ancestrytree.data.Person;
import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;

/**
 * This class tests the TraversalFileReader
 * @author Ady Francis
 *
 */
public class TraversalFileReaderTest {

	private String testFile = "input/small-preorder.txt";
	private TraversalFileReader reader;
	
	/**
	 * Tests the constructor and getList() method.
	 */
	@Test
	public void testTraversalFileReader() {

		try {
			reader = new TraversalFileReader("invalid");
			fail();
		}
		catch (FileNotFoundException fne) {
			assertNull(reader);
		}
		try {
			reader = new TraversalFileReader(testFile);
		}
		catch (FileNotFoundException fne) {
			fail();
		}
		
		MultiPurposeList<Person> list = reader.getList();
		
		assertEquals("Leonard, Henry", list.get(0).toString());
		assertEquals('M', list.get(0).getGender());
		
		assertEquals("Smith, Teresa", list.get(1).toString());
		assertEquals('F', list.get(1).getGender());
		
		assertEquals("Jones, Xena", list.get(2).toString());
		assertEquals('F', list.get(2).getGender());
		
		assertEquals("Doe, Zachary", list.get(3).toString());
		assertEquals('M', list.get(3).getGender());
		
		assertEquals("Murray, Claire", list.get(4).toString());
		assertEquals('F', list.get(4).getGender());
		
		assertEquals("Williams, Richard", list.get(5).toString());
		assertEquals('M', list.get(5).getGender());
		
		assertEquals("Perry, Percy", list.get(6).toString());
		assertEquals('M', list.get(6).getGender());
	}

}
