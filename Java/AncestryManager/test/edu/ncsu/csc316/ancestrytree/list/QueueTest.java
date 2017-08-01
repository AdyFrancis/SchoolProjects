package edu.ncsu.csc316.ancestrytree.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This tests the Queue class.
 * @author Ady Francis
 *
 */
public class QueueTest {

	/** Capacity of the queue*/
	private static final int CAPACITY = 10;
	
	/** Array of expected values */
	private static final String[] EXPECTED = {"1", "2", "3", "4", "5",
											  "6", "7", "8", "9", "10"};

	
	

	/**
	 * Tests the parameterized and default constructor.
	 */
	@Test
	public void testConstructor() {

		Queue<String> q1 = new Queue<String>();
		assertTrue(q1.isEmpty());
		assertEquals(0, q1.size());
		assertNull(q1.dequeue());
		assertNull(q1.peek());
		
		Queue<Integer> q2 = new Queue<Integer>(CAPACITY);
		assertTrue(q2.isEmpty());
		assertEquals(0, q2.size());
		assertNull(q2.dequeue());
		assertNull(q2.peek());
	}
	
	/**
	 * Tests the enqueue operation
	 */
	@Test
	public void testEnqueue() {
		
		int testSize = 0;
		
		Queue<String> q = new Queue<String>(CAPACITY);
		for (int i = 1; i <= CAPACITY; i++) {
			q.enqueue(Integer.toString(i));
			testSize++;
			assertEquals(testSize, q.size());
			assertFalse(q.isEmpty());
		}
		
		assertEquals(CAPACITY, q.size());
		assertFalse(q.isEmpty());

		try {
			q.enqueue(Integer.toString(31));
			fail();
		}
		catch (IllegalStateException ise) {
			assertEquals("1", q.peek());
		}
	}
	
	/**
	 * Tests the dequeue operation
	 */
	@Test
	public void testDequeue() {
				
		Queue<String> q = new Queue<String>(CAPACITY);
		int testSize = CAPACITY;
		
		for (int i = 1; i <= CAPACITY; i++) {
			q.enqueue(Integer.toString(i));
			assertFalse(q.isEmpty());
		}
		
		for (int i = 0; i < EXPECTED.length; i++){
			assertEquals(EXPECTED[i], q.dequeue());
			testSize--;
			assertEquals(testSize, q.size());
		}
		
		assertTrue(q.isEmpty());
		assertNull(q.dequeue());
	}
	
	/**
	 * Tests the peek operation
	 */
	@Test
	public void testPeek() {
		
		Queue<String> q = new Queue<String>(CAPACITY);
		for (int i = 1; i <= CAPACITY; i++) {
			q.enqueue(Integer.toString(i));
		}
		
		for (int i = 0; i < EXPECTED.length; i++){
			assertEquals(EXPECTED[i], q.peek());
			q.dequeue();
		}
		
		assertNull(q.peek());
		assertTrue(q.isEmpty());

	}

}
