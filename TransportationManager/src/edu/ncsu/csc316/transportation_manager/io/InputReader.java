package edu.ncsu.csc316.transportation_manager.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.ncsu.csc316.transportation_manager.highway.Highway;
import edu.ncsu.csc316.transportation_manager.list.MultiPurposeList;


/**
 * Reads an input file and constructs a list of highways
 * @author Ady Francis
 *
 */
public class InputReader {

	private String fileName;
	private BufferedReader br;
	
	
	/**
	 * Parameterized constructor. Exits if invalid file name is passed.
	 * @param fileName the name of the file to be read.
	 * @throws FileNotFoundException if file is not found
	 */
	public InputReader (String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		if (!f.exists()){
			throw new FileNotFoundException();
		}
		
		br = new BufferedReader(new FileReader(fileName));
		this.fileName = fileName;
	}
	
	/**
	 * This method reads the input file and adds entries
	 * to the MPL that will be returned.
	 * @return MultiPurposeList of type Highways
	 */
	public MultiPurposeList<Highway> getList() {
		
		MultiPurposeList<Highway> list = new MultiPurposeList<Highway>();
		String line;
		
		try {
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(" ");
				if (tokens.length != 4)
					throw new IOException();
				Highway h = new Highway(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
				if (h != null)
					list.add(h);
			}
			br.close();
		}
		catch (IOException io) {
			System.out.println("Error reading " + fileName + ". Exiting.");
			System.exit(1);
		}
		return list;
	}
	
	
}
