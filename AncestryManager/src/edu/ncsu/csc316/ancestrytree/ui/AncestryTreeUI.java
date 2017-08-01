package edu.ncsu.csc316.ancestrytree.ui;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.ncsu.csc316.ancestrytree.manager.AncestryTreeManager;

/**
 * User interface for the AncestryTreeManager
 * @author Ady Francis
 *
 */
public class AncestryTreeUI {
	
	
	static JFrame frame = null;
	/**
	 * Starting point of the program. Console user interface instead of GUI
	 * because of time constraints. 
	 * @param args command line arguments
	 */
	public static void main (String[] args) {
		
		
		System.out.println("Welcome to Ancestry Tree Manager.");
		System.out.print("Please enter the name of the file(s): ");
		Scanner scan = new Scanner(System.in);
		String in = "";
		in = scan.nextLine();
		String[] input = in.split(" ");
		frame = new JFrame();
		AncestryTreeManager atm = null;
		if (input.length == 1)
			atm = new AncestryTreeManager(input[0]);
		else if (input.length == 2)
			atm = new AncestryTreeManager(input[0], input[1]);
		else {
			JOptionPane.showMessageDialog(frame, "Invalid input file(s)");
			System.exit(1);
		}
		
		System.out.println(atm.getLevelOrder());
		String line = "";
		
		while (!line.equalsIgnoreCase("quit")) {
			System.out.println("Enter a name to determine a relationship. Or type quit to exit");
			line = scan.nextLine();
			if (line.equalsIgnoreCase("quit"))
				System.exit(0);
			System.out.println(atm.getRelationship(line));
		}
		
		scan.close();
			
	}
}
