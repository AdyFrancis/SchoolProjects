/**
 * 
 */
package edu.ncsu.csc316.compression_manager.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.ncsu.csc316.compression_manager.compressor.Compressor;
import edu.ncsu.csc316.compression_manager.data.Token;
import edu.ncsu.csc316.compression_manager.io.TextFileReader;
import edu.ncsu.csc316.compression_manager.util.Iterator;
import edu.ncsu.csc316.compression_manager.util.LinkedList;

/**
 * @author Ady Francis
 *
 */
public class CompressionManager {
	
	public String fileName;
	public LinkedList<Token> list;

	public CompressionManager() {
		
		
		this.list = new LinkedList<Token>();
		String name = getFileName();
		File file = new File(name);
		if (file.exists()) {
			this.fileName = name;
			
		}
		else {
			System.out.println("Invalid file name!");
			System.exit(0);
		}
		
		
		String mode = process(this.fileName);
		
		if (mode.equals("EMPTY")) {
			System.out.println("File is empty");
			System.exit(0);
		}
		else if (mode.equals("COMPRESS"))
		{
			Compressor c = new Compressor(fileName);
			c.compress();
		}
			
			
	}
	
	public String getFileName() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	
		System.out.println("Enter the file name.");
		String file;
		try {
			file = in.readLine();
		}
		catch (IOException io){
			return null;
		}
		
		return file;
	}
	
	/**
	 * The process method accepts a file name as a parameter. The
	 * method then compreses or decompresses the file, based on 
	 * whether the file begins with "0" (if so, decompress the file;
	 * if not, compress the  file).
	 * @param fileName the Name of the file to process
	 * @return	"DECOMPRESS" if the file was decompressed
	 * 			"COMPRESS" if the file was compressed
	 * 			"EMPTY" if the file is empty (has no contents)
	 */
	public String process(String fileName) {
		
		TextFileReader reader = new TextFileReader(fileName, list);
		
		String first = reader.getFirstChar();
		

		if (first.equals("0"))
			return "DECOMPRESS";
		else if (first.isEmpty() || first == null)
			return "EMPTY";
		else
			return "COMPRESS";
		
	}
	
	
	public static void main(String[] args)
	{

		CompressionManager cm = new CompressionManager();
	}
}
