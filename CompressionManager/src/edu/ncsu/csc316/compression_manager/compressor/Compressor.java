package edu.ncsu.csc316.compression_manager.compressor;

import edu.ncsu.csc316.compression_manager.data.Token;
import edu.ncsu.csc316.compression_manager.io.TextFileReader;
import edu.ncsu.csc316.compression_manager.util.Iterator;
import edu.ncsu.csc316.compression_manager.util.LinkedList;

/**
 * 
 * @author Ady Francis
 *
 */
public class Compressor {
	
	LinkedList<Token> compressed;
	String fileName;
	
	public Compressor(String fileName){
		this.fileName = fileName;
		compressed = new LinkedList<Token>();
	}

	public LinkedList<Token> compress() {
		
		TextFileReader reader = new TextFileReader(fileName, compressed);
		
		compressed = reader.compressFile();
		System.out.println(compressed.print());
		
		return compressed;
	}
}
