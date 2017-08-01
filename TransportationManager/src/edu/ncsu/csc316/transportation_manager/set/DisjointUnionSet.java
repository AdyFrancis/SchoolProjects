package edu.ncsu.csc316.transportation_manager.set;

/**
 * Disjoint set structure for MST
 * Represents the cities in the MST
 * @author Ady
 *
 */
public class DisjointUnionSet {
	
	private int vertices[];
	
	/**
	 * Creates an instance of the DSU
	 * @param x the total number of cities
	 */
	public DisjointUnionSet (int x) {
		vertices = new int[x];
		for (int i = 0; i < x; i++)
			vertices[i] = i;
	}
	
	/**
	 * Finds the set x belongs to 
	 * @param x the integer to find
	 * @return the location of the integer
	 */
	int find (int x) 
	{
		if (vertices[x] != x)
			vertices[x] = find (vertices[x]);
		
		return vertices[x];
	}
	
	/**
	 * Unions two sets together
	 * @param v1 vertex to union
	 * @param v2 vertex to union
	 */
	public void union (int v1, int v2) {
		int x = find(v1);
		int y = find (v2);
		
		vertices[y] = x;
		
	}

}
