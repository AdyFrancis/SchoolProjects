package edu.ncsu.csc316.spell_checker.io;

import edu.ncsu.csc316.spell_checker.hash_table.HashTable;
import edu.ncsu.csc316.spell_checker.list.MultiPurposeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class parses both the dictionary file to store in the hash tabel
 * and also checks input files for spelling errors
 * @author Ady Francis
 *
 */
public class TextFileReader {

	
	private String fileName;
	private BufferedReader bf;
	private HashTable dictionary;
	
	/**
	 * Parameterized constructor for the FileReader
	 * @param fileName the name of the file to read
	 */
	public TextFileReader(String fileName) throws FileNotFoundException {
		
		File f = new File(fileName);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		
		this.bf = new BufferedReader(new FileReader(fileName));
	}
 
	
	/**
	 * Reads an input file, storing each string in a hash table
	 * @return HashTable storing all the strings in the input file
	 */
	public HashTable hashInput() {
		dictionary = new HashTable();
		
		String line;
		
		try {
			while ((line = bf.readLine()) != null) {
				dictionary.add(line);
			}
			bf.close();
		}
		catch (IOException io) {
			System.out.println("Error reading " + fileName + ". Exiting.");
			System.exit(1);

		}
		return dictionary;
	}
	
	/**
	 * Checks to see if the string is misspelled according to the dictionary
	 * @param s the string to check
	 * @return false if the string is misspelled, true if not
	 */
	private boolean find(String s)
	{
		int x, i = 0;
		boolean esBool = false, erBool = false, edBool = false, ingBool = false, lyBool = false;
		while ((x = dictionary.retrieve(s)) == -1) {
			int length = s.length();
			if (i == 0 && Character.isUpperCase(s.charAt(0))) {
				char c = Character.toLowerCase(s.charAt(0));
				s = Character.toString(c) + s.substring(1);
			}
			else if(i == 1 && length >= 2 && s.substring(s.length() - 2).equals("'s")) {
				s = s.substring(0, s.length() - 2);
			}
			else if (i > 1) {
				
				if (!esBool &&  length >= 2 && s.substring(s.length() - 2).equals("es")) {
					s = s.substring(0, s.length() - 2);
					if (dictionary.retrieve(s) == -1) {
						String temp = s + "e";
						if (dictionary.retrieve(temp) != -1)
							return true;
					}
					esBool = true;
				}
				else if(!esBool && length > 1 && s.substring(s.length() - 1).equals("s") ) {
					s = s.substring(0, s.length() - 1 );
					esBool = true;
				}
				else if(!erBool && length >= 2 && s.substring(s.length() - 2).equals("er")) {
					s = s.substring(0, s.length() - 2);
					if (dictionary.retrieve(s) == -1) {
						String temp = s + "e";
						if (dictionary.retrieve(temp) != -1)
							return true;
					}
						
					erBool = true;
				}
				else if(!erBool  && length > 1 &&  s.substring(s.length() - 1).equals("r")) {
					s = s.substring(0, s.length() - 1);
					erBool = true;
				}
				else if(!edBool && length >= 2 && s.substring(s.length() - 2).equals("ed")) {
					s = s.substring(0, s.length() - 2);
					if (dictionary.retrieve(s) == -1) {
						String temp = s + "e";
						if (dictionary.retrieve(temp) != -1)
							return true;
					}
					edBool = true;
				}
				else if(!edBool && length > 1 && s.substring(s.length() - 1).equals("d")) {
					s = s.substring(0, s.length() - 1 );
					edBool = true;
				}
				else if(!ingBool && length > 3 &&  s.substring(s.length() - 3).equals("ing")) {
					s = s.substring(0, s.length() - 3);
					String temp = s + "e";
					if (dictionary.retrieve(temp) != -1)
						return true;
					ingBool = true;
				}
				else if(!lyBool && length >= 2 && s.substring(s.length() - 2).equals("ly")) {
					s = s.substring(0, s.length() - 2);
					lyBool = true;
				}
				
				if(!esBool && !erBool && !edBool && !ingBool && !lyBool || i >= 9)
					return false;
			}
				
			i++;
		}
		
		return !(x == -1);
	}
	
	/**
	 * Spell checks the given input file
	 * @param h hash table that contains the dictionary
	 * @param fileName the name of the input file
	 * @return an alphabetized list of errors
	 * @throws FileNotFoundException if the input file name is invalid
	 */
	public MultiPurposeList<String> checkFile(HashTable h, String fileName) throws FileNotFoundException {
		
		this.dictionary = h;
		File f = new File(fileName);
		if (!f.exists())
			throw new FileNotFoundException();
		BufferedReader fr = new BufferedReader(new FileReader(fileName));
		MultiPurposeList<String> misspelled = new MultiPurposeList<String>();
		
		String line;
		
		try {
			while ((line = fr.readLine()) != null) {
				String tokens[] = line.split(" ");
				for (String s: tokens) {
					if(s !=  null && s.length() > 0  && !find(s) && misspelled.indexOf(s) == -1)
						misspelled.addSorted(s);
				}
			}
			fr.close();
		}
		catch (IOException io) {
			System.out.println("Error reading " + fileName + ". Exiting.");
			System.exit(1);

		} 
		
		return misspelled;
	}
	
	

}
