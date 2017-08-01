package edu.ncsu.csc316.ancestrytree.io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc316.ancestrytree.data.AhnentafelEntry;
import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;


/**
 * This class tests the AhnentafelFileReader
 * @author Ady Francis
 *
 */
public class AhnentafelFileReaderTest {
	
	private String testFile = "input/small-ahnentafel.txt";
	private AhnentafelFileReader afr;

	/**
	 * Tests the constructor and getList() method.
	 */
	@Test
	public void testAhnentafelFileReader() {
		

		try {
			afr = new AhnentafelFileReader("invalid");
			fail();
		}
		catch (FileNotFoundException fne) {
			assertNull(afr);
		}
		try {
			afr = new AhnentafelFileReader(testFile);
		}
		catch (FileNotFoundException fne) {
			fail();
		}
		
		MultiPurposeList<AhnentafelEntry> list = afr.getList();
		
		assertEquals("Smith, Charles", list.get(1).toString());
		assertEquals(2, list.get(1).getNum());
		
		assertEquals("Poole, Sally", list.get(10).toString());
		assertEquals(11, list.get(10).getNum());
		
		assertEquals("Jones, Betty", list.get(2).toString());
		assertEquals(3, list.get(2).getNum());
		
		assertEquals("Williams, Jane", list.get(4).toString());
		assertEquals(5, list.get(4).getNum());
		
		assertEquals("Williams, Richard", list.get(9).toString());
		assertEquals(10, list.get(9).getNum());
		
		assertEquals("Jones, William", list.get(5).toString());
		assertEquals(6, list.get(5).getNum());
		
		assertEquals("Smith, Billy", list.get(0).toString());
		assertEquals(1, list.get(0).getNum());
		
		assertEquals("King, Catherine", list.get(6).toString());
		assertEquals(7, list.get(6).getNum());
		
		assertEquals("Smith, Jimmy", list.get(7).toString());
		assertEquals(8, list.get(7).getNum());
		
		assertEquals("Harper, Joanne", list.get(8).toString());
		assertEquals(9, list.get(8).getNum());
		
		assertEquals("Smith, Henry", list.get(3).toString());
		assertEquals(4, list.get(3).getNum());
		
	}

}
