package edu.ncsu.csc316.ancestrytree.data;

/**
 * This class represents a Person data type.
 * @author Ady Francis
 *
 */
public class Person {
	
	private String name;
	private char gender;
	
	/**
	 * Parameterized constructor for a Person
	 * @param name name of the person
	 * @param gender gender of the person 
	 */
	public Person(String name, char gender) {
		this.name = name;
		
		if (Character.isLowerCase(gender))
			this.gender = Character.toUpperCase(gender);
		else this.gender = gender;
	}

	/**
	 * This method returns the name of the person
	 * @return the name of the person
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method returns the gender of the person
	 * @return the gender of the person
	 */
	public char getGender() {
		return gender;
	}
	
	/**
	 * Checks to see if a person is equal to another person
	 * @param o person object to compare
	 * @return true if equal false if not
	 */
	@Override
	public boolean equals(Object o) {
		Person p = (Person) o;
		return (name.equals(p.getName()) && gender == p.getGender());
	}
	
	/**
	 * Returns a string representation of the person in the form of
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
