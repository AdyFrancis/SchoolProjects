import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	private String file;
	private BufferedReader bf;
	private ArrayList<Point> output;
	
	
	public Parser (String file) {
		this.file = file;
		try {
			parse();
		}
		catch (FileNotFoundException fnf) {
			System.out.println("File " + file + " was not found!");
		}
	}
	
	private void parse() throws FileNotFoundException  {
		String line;
		output = new ArrayList<Point>();
		
		File f = new File (file);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		
		this.bf = new BufferedReader(new FileReader(file));
		
		try {
			while ((line = bf.readLine()) != null) {
				String tokens[] = line.split(" ");
				for (String s: tokens) {
					String coord[] = s.split(",");
					Point p = new Point(Double.parseDouble(coord[0]),Double.parseDouble(coord[1]));
					output.add(p);
					System.out.println("x: " + p.getX() + " y: " + p.getY());
				}
					
			}
		}
		catch (IOException io) {
			System.out.println("Error reading " + file + ". Exiting.");
			io.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("number of points processed: " + output.size());
		
	}
	
	public ArrayList<Point> getPointArray() {
		return output;
	}


	
}
