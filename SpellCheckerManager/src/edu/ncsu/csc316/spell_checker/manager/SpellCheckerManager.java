package edu.ncsu.csc316.spell_checker.manager;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.ncsu.csc316.spell_checker.hash_table.HashTable;
import edu.ncsu.csc316.spell_checker.io.TextFileReader;
import edu.ncsu.csc316.spell_checker.list.MultiPurposeList;

/**
 * This class manages the SpellChecker application
 * @author Ady Francis
 *
 */
public class SpellCheckerManager {

	
	private MultiPurposeList<String> misspelled;
	private HashTable dictionary;
	private TextFileReader reader;
	
	
	private static JFrame frame = null;
	
	/**
	 * Constructs a new Spell Checker with the given dictionary
	 * 
	 * @param pathToDictionary the path to the dictionary
	 */
	 public SpellCheckerManager(String pathToDictionary) 
	 {
		 
		 try {
			 setReader(pathToDictionary);
		 }
		 catch (FileNotFoundException fne) {
				JOptionPane.showMessageDialog(frame, pathToDictionary + "(The system cannot find the file specified.)");
				System.exit(1);
		 }
		 
		 this.dictionary = reader.hashInput();
	 }
	 
	 
	 /**
	  * Sets the TextFileReader for this instance of the SCM
	  * @param pathToDictionary the path to the dictionary file
	  * @throws FileNotFoundException if the file name is invalid
	  */
	 private void setReader(String pathToDictionary) throws FileNotFoundException
	 {
		 
		 reader = new TextFileReader(pathToDictionary);
	 }
	 
	 /**
	  * Checks a given file for spelling errors
	  * @param pathToFile the path to the file to check for errors
	  * @throws FileNotFoundException if the file name is invalid
	  */
	 private void getList(String pathToFile) throws FileNotFoundException
	 {
		 this.misspelled = reader.checkFile(this.dictionary, pathToFile);
	 }
	 
	 /**
	  * Returns a string representation of the list of misspelled
	  * words (in alphabetical order, case insensitive) in the input file.
	  * The string representation should be in the format:
	  * ArrayBasedList[a, b, c]
	  * 
	  * @param pathToFile the path to the file to be spell-checked
	  * @return a string representation of the list of misspelled words
	  */
	 public String spellCheck(String pathToFile)
	 {
		 try {
			getList(pathToFile);
		 }
		catch (FileNotFoundException fne) {
			return "File not found.";
		}
			 return "ArrayBasedList[" + misspelled.toString() + "]";
	 }
	 
}
