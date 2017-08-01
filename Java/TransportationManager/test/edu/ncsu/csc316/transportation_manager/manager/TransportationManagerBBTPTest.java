package edu.ncsu.csc316.transportation_manager.manager;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * ECP & BVA Black box test cases.
 * @author Ady
 *
 */
public class TransportationManagerBBTPTest {

	
	
	/**
	 * Tests the ECP test case.
	 */
	@Test
	public void testECP() {
		TransportationManager tm = new TransportationManager("input/input-1.txt");
		
		assertEquals("Heap[\n" + 
				"   Highway[city1=1, city2=2, cost=7.0, asphalt=159.0],\n" + 
			    "   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0],\n" + 
			    "   Highway[city1=1, city2=0, cost=14.0, asphalt=415.0]\n]"
			    , tm.getMinHighwayHeap("COST"));
		
		assertEquals("List[\n" + 
				"   Highway[city1=1, city2=0, cost=14.0, asphalt=415.0],\n" + 
				"   Highway[city1=1, city2=2, cost=7.0, asphalt=159.0],\n" +
				"   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0]\n]", 
				tm.getMinimumHighways("COST"));
		
		assertEquals("AdjacencyList[\n" + 
				  "   City 0: -> Highway[city1=1, city2=0, cost=14.0, asphalt=415.0]\n" +
			      "   City 1: -> Highway[city1=1, city2=0, cost=14.0, asphalt=415.0] -> Highway[city1=1, city2=2, cost=7.0, asphalt=159.0]\n" +
			      "   City 2: -> Highway[city1=1, city2=2, cost=7.0, asphalt=159.0] -> Highway[city1=3, city2=2, cost=12.0, asphalt=212.0]\n" +
			      "   City 3: -> Highway[city1=3, city2=2, cost=12.0, asphalt=212.0]\n]"
			      , tm.getAdjacencyList());
	}
	
	/**
	 * Tests the BVA case.
	 */
	@Test 
	public void testBVA() {
		TransportationManager tm = new TransportationManager("input/input-2.txt");
		
		
		assertEquals("Heap[\n" + 
				"   Highway[city1=0, city2=1, cost=7.0, asphalt=159.0],\n" + 
			    "   Highway[city1=0, city2=1, cost=7.0, asphalt=159.0]\n]"
			    , tm.getMinHighwayHeap("ASPHALT"));
		
		assertEquals("List[\n" + 
				"   Highway[city1=0, city2=1, cost=7.0, asphalt=159.0]\n]", 
				tm.getMinimumHighways("ASPHALT"));
		
		assertEquals("AdjacencyList[\n" + 
				  "   City 0: -> Highway[city1=0, city2=1, cost=7.0, asphalt=159.0] -> Highway[city1=0, city2=1, cost=7.0, asphalt=159.0]\n" +
			      "   City 1: -> Highway[city1=0, city2=1, cost=7.0, asphalt=159.0] -> Highway[city1=0, city2=1, cost=7.0, asphalt=159.0]\n]"
			      , tm.getAdjacencyList());

	}
}
