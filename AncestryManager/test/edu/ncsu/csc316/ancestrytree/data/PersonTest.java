package edu.ncsu.csc316.ancestrytree.data;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This tests the person class.
 * @author Ady Francis
 *
 */
public class PersonTest {

	/**
	 * Tests the constructor and the getName, getGender,toString, and equals methods
	 */
	@Test
	public void testPerson() {
		Person m = new Person("John Doe", 'm');
		Person f = new Person("Jane Doe", 'F');
		
		assertEquals("John Doe", m.getName());
		assertEquals('M', m.getGender());
		assertEquals("Jane Doe", f.getName());
		assertEquals('F', f.getGender());
		
		assertEquals("Doe, John", m.toString());
		assertEquals("Doe, Jane", f.toString());
		
		assertFalse(m.equals(f));
		
		Person m1 = new Person("John Doe", 'M');
		assertTrue(m1.equals(m));
	}

}
