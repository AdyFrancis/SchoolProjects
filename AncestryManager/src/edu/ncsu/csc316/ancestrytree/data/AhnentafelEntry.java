package edu.ncsu.csc316.ancestrytree.data;

/**
 * This class represents the data type
 * that the AhnentafelTree uses.
 * @author Ady Francis
 *
 */
public class AhnentafelEntry {
	
	private String name;
	private int num;
	
	
	/**
	 * Parameterized constructor for an Entry
	 * @param name name of the entry
	 * @param num number assigned to the entry 
	 */
	public AhnentafelEntry(String name, int num) {
		this.name = name;
		this.num = num;
	}
	
	/**
	 * This method returns the name of the entry
	 * @return the name of the entry
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method returns the number assigned to the entry
	 * @return the number assigned to the entry
	 */
	public int getNum() {
		return num;
	}
	
	/**
	 * Returns a string representation of the entry in the form of
	 * LastName, FirstName
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		int index = name.indexOf(' ');
		String firstName = name.substring(0, index);
		String lastName = name.substring(index + 1);
		
		return lastName + ", " + firstName;
	}
}
