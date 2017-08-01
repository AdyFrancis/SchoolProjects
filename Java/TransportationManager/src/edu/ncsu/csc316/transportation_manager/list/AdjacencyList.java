package edu.ncsu.csc316.transportation_manager.list;

import edu.ncsu.csc316.transportation_manager.highway.Highway;

/**
 * This class is an instance of an adjacency list (AL)
 * that represents cities as list and highways as edges. 
 * @author Ady
 *
 */
public class AdjacencyList {
	
	/**
	 * Represents the vertices in the adjacency list
	 * @author Ady
	 *
	 */
	private class City {
		private MultiPurposeList<Highway> edges;
		private int id;
		
		/**
		 * Constructs an instance of the City object
		 * @param id the id of city
		 */
		public City(int id) {
			this.id = id;
			edges = new MultiPurposeList<Highway>();
		}
		
		/**
		 * Returns the id of the city. 
		 * @return the id of the city 
		 */
		public int getId(){
			return id;
		}
		
		/**
		 * Returns the list of edges for the vertex
		 * @return list of edges for a vertex
		 */
		public MultiPurposeList<Highway> getEdges() {
			return edges;
		}
	}
	
	private MultiPurposeList<City> list;
	private MultiPurposeList<Highway> highways;
	
	/**
	 * Constructs an instance of the Adjacency List
	 * @param highways the list of edges for the adjacency list
	 */
	public AdjacencyList(MultiPurposeList<Highway> highways){
		list = new MultiPurposeList<City>();
		this.highways = highways;
		construct();
	}
	
	/**
	 * Constructs the AL. 
	 */
	private void construct() {
		//finds the max city num
		int max = 0;
		for (int i = 0; i < highways.size(); i++) {
			Highway h = highways.get(i);
			max = Math.max(max, h.getCity1());
			max = Math.max(max, h.getCity2());
		}
		list.setSize(max + 1);
		
		//adds vertices
		for (int i = 0; i < list.size(); i++) {
			City c = new City(i);
			list.addAt(c, i);
		}
		
		//adds edges by city
		for (int i = 0; i < list.size(); i++) {
			City c = list.get(i);
			MultiPurposeList<Highway> edges = c.getEdges();
			for (int j = 0; j < highways.size(); j++) {
				Highway h = highways.get(j);
				if (h.getCity1() == c.getId() || h.getCity2() == c.getId()) {
					edges.addSorted(h);
				}
			}
		}
		
	}
	
	/**
	 * String representation of the adjacency list
	 * @return a string representation of the AL
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("AdjacencyList[\n");
		for (int i = 0; i < list.size(); i++) {
			str.append("   ");
			City c = list.get(i);
			str.append("City " + c.getId() + ": -> ");
			MultiPurposeList<Highway> edges = c.getEdges();
			
			for (int j = 0; j < edges.size(); j++) {
				if (j < edges.size() - 1)
					str.append(edges.get(j).toString() + " -> ");
				else
					str.append(edges.get(j).toString());
			}
			str.append("\n");
		}
		str.append("]");
		return str.toString();

	}
	
	
	

}
