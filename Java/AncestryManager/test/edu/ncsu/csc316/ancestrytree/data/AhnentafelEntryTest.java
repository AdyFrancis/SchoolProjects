package edu.ncsu.csc316.ancestrytree.data;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This tests the AhnentafelEntry class.
 * @author Ady Francis
 *
 */
public class AhnentafelEntryTest {

	/**
	 * Tests the constructor and the getName and getNum methods
	 */
	@Test
	public void testAhenentafelEntryClass() {
		
		AhnentafelEntry a = new AhnentafelEntry("John Doe", 1);
		AhnentafelEntry b = new AhnentafelEntry("Jane Doe", 2);
		
		assertEquals("John Doe", a.getName());
		assertEquals(1, a.getNum());
		assertEquals("Jane Doe", b.getName());
		assertEquals(2, b.getNum());
	}

}
