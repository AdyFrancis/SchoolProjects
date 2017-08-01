package edu.ncsu.csc316.transportation_manager.manager;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.ncsu.csc316.transportation_manager.heap.MinHighwayHeap;
import edu.ncsu.csc316.transportation_manager.highway.Highway;
import edu.ncsu.csc316.transportation_manager.io.InputReader;
import edu.ncsu.csc316.transportation_manager.list.AdjacencyList;
import edu.ncsu.csc316.transportation_manager.list.MultiPurposeList;
import edu.ncsu.csc316.transportation_manager.set.UpTree;

/**
 * The main component of the program that returns
 * the representation of the highways based on 
 * type.
 * @author Ady
 *
 */
public class TransportationManager {

	
	private MultiPurposeList<Highway> highwayList;
	
	private static JFrame frame = null;
	
	/**
	 * Constructs a new TransportationManager
	 * 
	 * @param pathToFile the path to the file that contains the set of highways in the graph
	 */
	public TransportationManager(String pathToFile)
	{
		InputReader ir = null;
		
		try {
			ir = new InputReader(pathToFile);
		}
		catch (FileNotFoundException fne) {
			JOptionPane.showMessageDialog(frame, pathToFile + "(The system cannot find the file specified.)");
			System.exit(1);
		}
		
		
		highwayList = ir.getList();
	}
	
	
	/**
	 * Returns a string representation of the AdjacencyList
	 * in the following format, where (for each city) the Highways are
	 * in sorted order by city1, then city2, then cost, then asphalt:
	 * @return the string representation of the AdjacencyLists
	 */
	public String getAdjacencyList()
	{
		AdjacencyList al = new AdjacencyList(highwayList);
		return al.toString();
	}
	
	
	/**
	* Returns a string representation of the MinHighwayHeap
	* @param type the type the highway should be sorted by
	* @return string representation of the minhighway
	*/
	public String getMinHighwayHeap(String type) {
		
		MinHighwayHeap mh = new MinHighwayHeap(type);
		for (int i = 0; i < highwayList.size(); i++)
			mh.insert(highwayList.get(i));
		return mh.toString();
	}
	
	
	/**
	 * Returns a string representation of the list of Highways contained in the 
	 * minimum spanning set of Highways. The returned string is in the following format,
	 * where the Highways are in sorted order by city1, city2, then cost, then asphalt:
	 * 
	 * @param type the type ("COST" or "ASPHALT") of field to minimize
	 * @return a string representation of the minimum spanning set of Highways
	 */
	public String getMinimumHighways(String type)
	{
		UpTree mst = new UpTree(type, highwayList);
		return mst.toString();
	}
}
