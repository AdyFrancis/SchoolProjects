/**
 * 
 */
package edu.ncsu.csc316.compression_manager.io;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;


import edu.ncsu.csc316.compression_manager.data.Token;
import edu.ncsu.csc316.compression_manager.util.Iterator;
import edu.ncsu.csc316.compression_manager.util.LinkedList;

/**
 * @author Ady Francis
 * This class reads in the text file.
 */
public class TextFileReader {

	String fileName;
	LinkedList<Token> list;
	
	public TextFileReader(String fileName, LinkedList<Token> list){
		this.fileName = fileName;
		this.list = list;
	}
	
	public LinkedList<Token> compressFile() {
		try {
			int ch;
			int index;
			String str = "";
			BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
			LinkedList<String> wordList = new LinkedList<String>();
			Iterator<String> itr = wordList.getIterator();
			
			while ((ch = fileReader.read())!= -1){
				char c = (char)ch;
				if (Character.isLetter(c)){
					str+=c;
				}
				else if (c == ' '){
					if (!str.isEmpty()) {
						str = str.toUpperCase();
						Token token = new Token(str, 1);
						index = itr.remove(str);

						if (index == -1) {
							wordList.add(str);
							list.add(token);
						}
						else {
							index++;
							wordList.add(str);

							list.add(new Token(Integer.toString(index), 1));
						}
						str = "";
					}
					Token space = new Token(" ", 0);
					list.add(space);
				}
				else {
					if (!str.isEmpty()){
						str = str.toUpperCase();
						Token token = new Token(str, 1);
						index = itr.remove(str);
						
						if (index == -1) {
							wordList.add(str);
							list.add(token);
						}
						else {
							wordList.add(str);
							System.out.println(str);
							System.out.println(wordList.print());
							index++;
							list.add(new Token(Integer.toString(index), 1));
						}
						str = "";
					}
					Token special = new Token(Character.toString(c), 2);
					list.add(special);
				}
			}
			
			fileReader.close();
			return list;
		}
		catch (IOException io) {
			System.out.println("Error in reading the file");
			return null;
		}
	}
	
	
	public String getFirstChar(){
		BufferedReader fileReader;
		String str = "";
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			str += (char)fileReader.read();
			fileReader.close();
			return str;

		} catch (IOException io) {
			System.out.println("Error reading file");
			return null;
		}
		
	}

	
	
}
