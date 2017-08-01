package edu.ncsu.csc316.transportation_manager.set;

import edu.ncsu.csc316.transportation_manager.heap.MinHighwayHeap;
import edu.ncsu.csc316.transportation_manager.highway.Highway;
import edu.ncsu.csc316.transportation_manager.list.MultiPurposeList;

/**
 * Represents an instance of a MST
 * @author Ady
 *
 */
public class UpTree {
	
	private String type;
	private MultiPurposeList<Highway> edges;
	private MultiPurposeList<Highway> mst;
	
	/**
	 * Parameterized constructor for up tree
	 * @param type the type of the MST
	 * @param edges the edges to find the MST for
	 */
	public UpTree (String type, MultiPurposeList<Highway> edges) {
		this.type = type;
		this.edges = edges;
		construct();
	}
	
	/**
	 * Returns the total number of cities
	 * @return total number of cities
	 */
	private int getVertices(){
		int x = 0;
		for (int i = 0; i < edges.size(); i++){
			Highway h = edges.get(i);
			x = Math.max(x, h.getCity1());
			x = Math.max(x, h.getCity2());
		}
		return x + 1;
	}
	
	/**
	 * Constructs an instance of the MST using Kruskal's
	 */
	private void construct() {
		if (edges.size() == 0)
			return;
		MinHighwayHeap heap = new MinHighwayHeap(this.type);
		for (int i = 0; i < edges.size(); i++)
			heap.insert(edges.get(i));
		
		this.mst = new MultiPurposeList<Highway>();
		int v = getVertices();
		DisjointUnionSet ds = new DisjointUnionSet(v);
		
		while (mst.size() < v - 1) {
			Highway h = heap.deleteMin();
			
			int c1 = ds.find(h.getCity1());
			int c2 = ds.find(h.getCity2());
			
			if (c1 != c2){
				mst.addSorted(h);
				ds.union(c1,  c2);
			}
			
		}
		
	}
	
	
	/**
	 * Returns a string representation of the MST
	 * @return string representation of the MST
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("List[\n");
		for (int i = 0; i < mst.size(); i++) {
			str.append("   " + mst.get(i).toString());
			if (i < mst.size() - 1)
				str.append(",");
			str.append("\n");
		}
		str.append("]");
		
		return str.toString();
	}

}
