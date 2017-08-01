package edu.ncsu.csc316.spell_checker.hash_table;

import java.util.Random;

import edu.ncsu.csc316.spell_checker.element.Element;
import edu.ncsu.csc316.spell_checker.list.MultiPurposeList;

/**
 * The Hash Table structure where dictionary words will be stored
 * @author Ady
 *
 */
public class HashTable {

	private final static int M_SIZE = 35923;
	private int size;
	private Element[] bucketArray;
	
	/**
	 * Constructor for the HashTable class
	 */
	public HashTable()
	{
		bucketArray = new Element[M_SIZE];
		size = 0;
	}

	/**
	 * Adds a string to the hash table
	 * @param word the word to add
	 */
	public void add (String word)
	{
		int index = compress(hashCode(word));
		if (get(index) != null)
			resolve(index, word);
		else
			bucketArray[index] = new Element(word);
		
		size++;
	}
	
	/**
	 * Returns the index where the provided string is located in the table
	 * @param word the word to retrieve
	 * @return index of the word in the array
	 */
	public int retrieve (String word)
	{
		int index = compress(hashCode(word));
		Element e = bucketArray[index];
		if (e == null)
			return -1;
		if (e.getString().equals(word))
			return index;
		else 
		{
			MultiPurposeList<String> collisions = e.getCollisions();
			for (int i = 0; i < collisions.size(); i++)
				if (collisions.get(i).equals(word))
					return index;
			return -1;
		}
	}
	
	/**
	 * Returns the string at the specified index
	 * @param index the index to get the string from
	 * @return the string at the index
	 */
	public Element get (int index)
	{
		return bucketArray[index];
	}
	
	/**
	 * Returns the size of the bucket array
	 * @return size of the array
	 */
	public int size()
	{
		return size;
	}
		
	/**
	 * Converts a string to its hashed value
	 * @param word the string to convert
	 * @return the hashed value
	 */
	private int hashCode(String word)
	{
		int hash = 29;
		int length = word.length();
		Random generator = new Random(hash << length);
		
		for (int i = 0; i < length; i++) {
			hash += (int)word.charAt(i);
			int r = hash >> generator.nextInt();
			hash = (hash << 5) | (hash >>> 27);
            hash = hash ^ r;
		}
		if (hash < 0)
			hash = hash * -1;
		return hash;
		
	}
	
	/**
	 * Compresses a hashed value into a valid bucket index
	 * @param hash the hash value to compress
	 * @return valid bucket index
	 */
	private int compress(int hash)
	{
		return hash % M_SIZE;
	}
	
	/**
	 * Resolves any collisions via chaining CRP
	 * @param index the index to resolve the collision at
	 * @param word the word to compare to. Can also be null
	 */
	private void resolve(int index, String word)
	{
		Element e = get(index);
		e.getCollisions().add(word);
	}
	
}