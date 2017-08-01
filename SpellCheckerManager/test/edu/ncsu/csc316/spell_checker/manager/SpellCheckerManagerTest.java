package edu.ncsu.csc316.spell_checker.manager;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * This tests the SpellCheckerManager class
 * @author Ady Francis
 *
 */
public class SpellCheckerManagerTest {

	/**
	 * Tests reading in a dictionary
	 * and spell checking multiple input files.
	 */
	@Test
	public void test() {		
		
		SpellCheckerManager scm = new SpellCheckerManager("input/dictionary.txt");
		assertEquals("ArrayBasedList[]", scm.spellCheck("input/small_sample.txt"));
		assertEquals("ArrayBasedList[lkars, teacherslys, zaf]", scm.spellCheck("input/bad2.txt"));
		assertEquals("File not found.", scm.spellCheck("INVALIDFILE.txt"));
	} 
}
