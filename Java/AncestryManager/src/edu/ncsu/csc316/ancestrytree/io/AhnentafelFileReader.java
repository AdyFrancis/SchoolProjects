package edu.ncsu.csc316.ancestrytree.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.ncsu.csc316.ancestrytree.data.AhnentafelEntry;
import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;

/**
 * This class reads the input ahnentafel file
 * and returns a MultiPurposeList to build the AhnentafelTree
 * @author Ady Francis
 *
 */
public class AhnentafelFileReader {
	
	private String fileName;
	private BufferedReader bf;
	
	/**
	 * Constructor for the AhnentafelFileReader
	 * @param fileName the name of the file to be read
	 * @throws FileNotFoundException if file is not found
	 */
	public AhnentafelFileReader  (String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
	
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		this.bf = reader;
		this.fileName = fileName;
	}
	
	
	/**
	 * Gets the number of entries in the ahnentafel file
	 * @return the number of entries in the file
	 */
	private int getNumEntries() {
		BufferedReader br = null;
		int max = 0;
		int num = 0;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				 num = Integer.parseInt(line.split(" ")[0]);
				 if (num > max)
					 max = num;
			}
			br.close();
			}
		catch (Exception fne) {
			System.out.println(fne.getMessage());
			System.exit(1);
		}
	
		return max;
		
	}
	
	/**
	 * Reads the input file and adds entries to the MPL 
	 * that will be returned as a list.
	 * @return MultiPurposeList of type AhnentafelEntry with all entries in order
	 * @throws FileNotFoundException if file cannot be read
	 */
	public MultiPurposeList<AhnentafelEntry> getList() {
		
		int numEntries = getNumEntries();
		
		MultiPurposeList<AhnentafelEntry> list = new MultiPurposeList<AhnentafelEntry>();
		list.setSize(numEntries);
		String line;
		try {
			while ((line = bf.readLine()) != null) {
				String[] tokens = line.split(" ");
				AhnentafelEntry a = new AhnentafelEntry(tokens[1] + " " + tokens[2], Integer.parseInt(tokens[0]));
				list.addAt(a.getNum() - 1, a);
			}
			bf.close();
		}
		catch (IOException io) {
			System.out.println("Error reading " + fileName + ". Exiting.");
			System.exit(1);
		}
		
		return list;
	}
	

}
