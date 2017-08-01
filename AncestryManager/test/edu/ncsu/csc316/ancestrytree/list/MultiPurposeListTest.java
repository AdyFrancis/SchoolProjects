package edu.ncsu.csc316.ancestrytree.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This tests the MultiPurposeList class.
 * @author Ady Francis
 */
public class MultiPurposeListTest {


	/**
	 * Tests the constructor
	 */
	@Test
	public void testConstructor() {
		MultiPurposeList<Integer> mpl = new MultiPurposeList<Integer>();
		assertTrue(mpl.isEmpty());
	}
	
	/**
	 * Tests the add method
	 */
	@Test
	public void testAdd() {
		
		MultiPurposeList<Integer> mpl = new MultiPurposeList<Integer>();
		
		//making sure that checkCapacity() is working
		for (int i = 0; i < 100; i++)
			mpl.add(i);
		
		assertEquals(100, mpl.size());
		
		for (int i = 0; i < 100; i++)
			assertEquals((Integer)i, mpl.get(i));
		
		MultiPurposeList<String> mpl2 = new MultiPurposeList<String>();
		
		assertTrue(mpl2.isEmpty());
		mpl2.add("foo");
		mpl2.add("bar");
		mpl2.add("foofoo");
		mpl2.add("barbar");
		mpl2.add("foobar");
		
		assertFalse(mpl2.isEmpty());
		assertEquals(5, mpl2.size());
		
		assertEquals(-1, mpl2.indexOf("invalid"));
		assertEquals(0, mpl2.indexOf("foo"));
		assertEquals(1, mpl2.indexOf("bar"));
		assertEquals(2, mpl2.indexOf("foofoo"));
		assertEquals(3, mpl2.indexOf("barbar"));
		assertEquals(4, mpl2.indexOf("foobar"));
	}
	
	
	/**
	 * Tests addAt method
	 */
	@Test
	public void testAddAt() {
		
		MultiPurposeList<String> mpl = new MultiPurposeList<String>();
		
		mpl.setSize(3);
		
		mpl.addAt(2, "Three");
		mpl.addAt(1, "Two");
		mpl.addAt(0, "One");
		
		assertEquals("One", mpl.get(0));
		assertEquals("Two", mpl.get(1));
		assertEquals("Three", mpl.get(2));
		
		assertEquals(3, mpl.size());
		assertFalse(mpl.isEmpty());
	}
	
	/** 
	 * Tests the remove method
	 */
	@Test
	public void testRemove() {
		
		MultiPurposeList<String> mpl = new MultiPurposeList<String>();
		
		assertTrue(mpl.isEmpty());
		mpl.add("foo");
		mpl.add("bar");
		mpl.add("foofoo");
		mpl.add("barbar");
		mpl.add("foobar");
		
		assertFalse(mpl.isEmpty());
		assertEquals(5, mpl.size());
		
		//removing from start
		assertEquals("foo", mpl.remove(0));
		assertEquals(4, mpl.size());
		//removing from end
		int last = mpl.size() - 1;
		assertEquals("foobar", mpl.remove(last));
		assertEquals(3, mpl.size()); //0 1 2
		
		try {
			mpl.remove(3);
			fail();
		}
		catch (IndexOutOfBoundsException i) {
			assertEquals("foofoo", mpl.remove(1));
		}
		
		
		assertEquals(2, mpl.size());
		assertEquals("bar", mpl.remove(0));
		assertEquals(1, mpl.size());
		//remove at invalid index
		assertEquals(-1, mpl.indexOf("foobar"));
		assertFalse(mpl.isEmpty());
		assertEquals("barbar", mpl.remove(0));
	}
	
	/**
	 * Tests the indexOf method
	 */
	@Test
	public void testIndexOf() {
		MultiPurposeList<String> mpl = new MultiPurposeList<String>();
		
		mpl.add("One");
		mpl.add("Two");
		mpl.add("Three");
		mpl.add("Four");
		
		assertEquals(4, mpl.size());
		
		assertEquals(0, mpl.indexOf("One"));
		assertEquals(1, mpl.indexOf("Two"));
		assertEquals(2, mpl.indexOf("Three"));
		assertEquals(3, mpl.indexOf("Four"));
		
		mpl.remove(2);
		
		assertEquals(-1, mpl.indexOf("Three"));
		assertEquals(0, mpl.indexOf("One"));
		assertEquals(1, mpl.indexOf("Two"));
		assertEquals(2, mpl.indexOf("Four"));
		mpl.remove(2);
		assertEquals(0, mpl.indexOf("One"));
		assertEquals(1, mpl.indexOf("Two"));
		mpl.add("Three");
		assertEquals(0, mpl.indexOf("One"));
		assertEquals(1, mpl.indexOf("Two"));
		assertEquals(2, mpl.indexOf("Three"));

	}
	
	/**
	 * Tests the get method
	 */
	@Test
	public void testGet() {
		MultiPurposeList<String> mpl = new MultiPurposeList<String>();
		
		mpl.add("One");
		mpl.add("Two");
		mpl.add("Three");
		mpl.add("Four");
		
		assertEquals("One", mpl.get(0));
		assertEquals("Two", mpl.get(1));
		assertEquals("Three", mpl.get(2));
		assertEquals("Four", mpl.get(3));
		
		mpl.remove(0);
		
		assertEquals("Two", mpl.get(0));
		assertEquals("Three", mpl.get(1));
		assertEquals("Four", mpl.get(2));
		
		mpl.remove(1);
		try {
			mpl.remove(2);
			fail();
		}
		catch (IndexOutOfBoundsException i) {
			assertEquals("Two", mpl.get(0));
			assertEquals("Four", mpl.get(1));
		}
		
		mpl.add("Five");
		mpl.add("Six");
		assertEquals("Two", mpl.get(0));
		assertEquals("Four", mpl.get(1));
		assertEquals("Five", mpl.get(2));
		assertEquals("Six", mpl.get(3));

	}

}
