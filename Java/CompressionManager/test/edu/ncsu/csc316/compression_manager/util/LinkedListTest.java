/**
 * 
 */
package edu.ncsu.csc316.compression_manager.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc316.compression_manager.util.LinkedList;

/**
 * @author Ady
 *
 */
public class LinkedListTest {

	LinkedList<String> list = new LinkedList<String>();


	@Test
	public void testLinkedListIterator() {
		
		
		assertEquals(0, list.size());
		
		list.add("This"); //3
		list.add("is"); //2
		list.add("a"); //1
		list.add("sentence."); //0
		
		assertEquals("sentence.", list.getFirstData());
		assertTrue(list.find("This"));
		assertTrue(list.find("is"));
		assertTrue(list.find("a"));
		assertTrue(list.find("sentence."));
		assertFalse(list.find("invalid"));
		Iterator<String> itr = list.getIterator();
		
		assertFalse(list.isEmpty());
		assertEquals(4, list.size());
		
		assertEquals("sentence.", itr.next());
		assertEquals("a", itr.next());
		assertEquals("is", itr.next());
		assertEquals("This", itr.next());
		assertFalse(itr.hasNext());
		assertTrue(itr.hasPrevious());
		assertEquals("This", itr.previous());
		assertEquals("is", itr.previous());
		assertEquals("a", itr.previous());
		assertEquals("sentence.", itr.previous());
		assertFalse(itr.hasPrevious());

		assertEquals(-1, itr.remove("INVALID"));
		assertEquals(3, itr.remove("This"));
		assertEquals(3, list.size());
		assertEquals(1, itr.remove("a"));
		
		assertEquals(2, list.size());
		assertEquals(0, itr.remove("sentence."));
		assertEquals(1, list.size());
		assertEquals(0, itr.remove("a"));
		assertTrue(list.isEmpty());
	
		

	}
	
	@Test
	public void testIsEmpty() {
		assertTrue(list.isEmpty());
	}

}
