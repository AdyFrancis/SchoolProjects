package edu.ncsu.csc316.spell_checker.element;

import edu.ncsu.csc316.spell_checker.list.MultiPurposeList;

/**
 * This class represents the words in the dictionary
 * that will be stored in the hash table.
 * @author Ady Francis
 *
 */
public class Element {
	
	private String word;
	private MultiPurposeList<String> collisions;
	
	/**
	 * Parameterized constructor
	 * @param word the data associated with the Element object
	 */
	public Element (String word)
	{
		this.word = word;
		collisions = new MultiPurposeList<String>();
	}
	
	/**
	 * Returns the string associated with this Element objects
	 * @return the string associated with the object
	 */
	public String getString()
	{
		return word;
	}
	
	/**
	 * Returns the list of strings that collided with this element
	 * @return an MPL of type String of all collisions with this element
	 */
	public MultiPurposeList<String> getCollisions()
	{
		return collisions;
	}

}
