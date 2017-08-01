package edu.ncsu.csc316.spell_checker.hash_table;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This tests the HashTable class
 * @author Ady Francis
 *
 */
public class HashTableTest {


	/**
	 * Tests adding and retrieving with the Hash Table
	 */
	@Test
	public void test() {
		HashTable h = new HashTable();
		h.add("test");
		h.add("tset");
		assertEquals(2, h.size());
		h.retrieve("null");		
		System.out.println("done");
	}

}
