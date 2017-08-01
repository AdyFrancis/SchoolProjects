package edu.ncsu.csc316.ancestrytree.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.ncsu.csc316.ancestrytree.data.Person;
import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;

/**
 * This class reads the input traversal files
 * and returns a MultiPurposeList to build the TraversalTree
 * @author Ady Francis
 *
 */
public class TraversalFileReader {
	
	private String fileName;
	private BufferedReader reader;
	
	/**
	 * Parameterized constructor. Exits if invalid file name is passed.
	 * @param fileName the name of the file to be read.
	 * @throws FileNotFoundException if file is not found
	 */
	public TraversalFileReader(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		BufferedReader bf = new BufferedReader(new FileReader(fileName));
		this.reader = bf;
		this.fileName = fileName;
	}
	
	/**
	 * This method reads the input file and adds entries
	 * to the MPL that will be returned.
	 * @return MultiPurposeList of type Person with all the entries in order.
 	 * @throws FileNotFoundException if file cannot be read
	 */
	public MultiPurposeList<Person> getList() {
		MultiPurposeList<Person> list = new MultiPurposeList<Person>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				Person p = new Person(tokens[0] + " " + tokens[1], tokens[2].charAt(0));
				list.add(p);
			}
			reader.close();
		}
		catch (IOException io) {
			System.out.println("Error reading " + fileName + ". Exiting.");
			System.exit(1);
		}
		return list;
	}
}
