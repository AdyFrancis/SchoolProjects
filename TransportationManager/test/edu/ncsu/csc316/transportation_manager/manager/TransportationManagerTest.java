package edu.ncsu.csc316.transportation_manager.manager;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Transportation Manager class
 * @author Ady
 *
 */
public class TransportationManagerTest {

	
	
	/**
	 * Tests constructing the adjacency list
	 */
	@Test
	public void testAdjacencyList() {
		TransportationManager tm = new TransportationManager("input/small.txt");
		assertEquals("AdjacencyList[\n" + 
					  "   City 0: -> Highway[city1=0, city2=3, cost=14.0, asphalt=415.0] -> Highway[city1=1, city2=0, cost=5.0, asphalt=99.0] -> Highway[city1=2, city2=0, cost=7.0, asphalt=159.0]\n" +
				      "   City 1: -> Highway[city1=1, city2=0, cost=5.0, asphalt=99.0] -> Highway[city1=1, city2=2, cost=6.0, asphalt=72.0] -> Highway[city1=3, city2=1, cost=10.0, asphalt=112.0]\n" +
				      "   City 2: -> Highway[city1=1, city2=2, cost=6.0, asphalt=72.0] -> Highway[city1=2, city2=0, cost=7.0, asphalt=159.0] -> Highway[city1=3, city2=2, cost=12.0, asphalt=212.0]\n" +
				      "   City 3: -> Highway[city1=0, city2=3, cost=14.0, asphalt=415.0] -> Highway[city1=3, city2=1, cost=10.0, asphalt=112.0] -> Highway[city1=3, city2=2, cost=12.0, asphalt=212.0]\n]"
				      , tm.getAdjacencyList());
		

	}
	
	
	/**
	 * Tests getting the min heap
	 */
	@Test
	public void testMinHeap() {
		TransportationManager tm = new TransportationManager("input/small.txt");
		assertEquals("Heap[\n" + 
				"   Highway[city1=1, city2=0, cost=5.0, asphalt=99.0],\n" + 
			    "   Highway[city1=2, city2=0, cost=7.0, asphalt=159.0],\n" + 
			    "   Highway[city1=1, city2=2, cost=6.0, asphalt=72.0],\n" + 
			    "   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0],\n" + 
			    "   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0],\n" + 
			    "   Highway[city1=0, city2=3, cost=14.0, asphalt=415.0]\n]"
			    , tm.getMinHighwayHeap("COST"));
					      
	}
	
	
	/**
	 * Tests getting the MST
	 */
	@Test
	public void testMST() {
		TransportationManager tm = new TransportationManager("input/small.txt");
		assertEquals("List[\n" + 
				"   Highway[city1=1, city2=0, cost=5.0, asphalt=99.0],\n" + 
				"   Highway[city1=1, city2=2, cost=6.0, asphalt=72.0],\n" +
				"   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0]\n]", 
				tm.getMinimumHighways("COST"));

		assertEquals("List[\n" + 
				"   Highway[city1=1, city2=0, cost=5.0, asphalt=99.0],\n" + 
				"   Highway[city1=1, city2=2, cost=6.0, asphalt=72.0],\n" +
				"   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0]\n]", 
				tm.getMinimumHighways("ASPHALT"));
		
	}
	

}
