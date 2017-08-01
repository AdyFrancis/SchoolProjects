package edu.ncsu.csc316.transportation_manager.heap;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc316.transportation_manager.highway.Highway;
import edu.ncsu.csc316.transportation_manager.io.InputReader;
import edu.ncsu.csc316.transportation_manager.list.MultiPurposeList;

/**
 * Tests the MinHighway Heap class
 * @author Ady
 *
 */
public class MinHighwayHeapTest {

	/**
	 * Tests the construction of a heap as well as deleteMin()
	 */
	@Test
	public void testHeap() {
		InputReader ir = null;
		
		try {
			ir = new InputReader("invalid.txt");
			fail();
		}
		catch (FileNotFoundException fne) {
			assertNull(ir);
			
			try { ir = new InputReader("input/small.txt"); }
		    catch (FileNotFoundException fn) { fail(); }
		}
		
		MultiPurposeList<Highway> list = ir.getList();
		
		MinHighwayHeap costHeap = new MinHighwayHeap("COST");
		MinHighwayHeap asphaltHeap = new MinHighwayHeap("ASPHALT");

		for (int i = 0; i < list.size(); i++) {
			costHeap.insert(list.get(i));
			asphaltHeap.insert(list.get(i));
		}
		
		assertEquals(6, costHeap.size());
		assertEquals(6, asphaltHeap.size());
		
		assertEquals("Heap[\n" + 
				"   Highway[city1=1, city2=0, cost=5.0, asphalt=99.0],\n" + 
			    "   Highway[city1=2, city2=0, cost=7.0, asphalt=159.0],\n" + 
			    "   Highway[city1=1, city2=2, cost=6.0, asphalt=72.0],\n" + 
			    "   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0],\n" + 
			    "   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0],\n" + 
			    "   Highway[city1=0, city2=3, cost=14.0, asphalt=415.0]\n]"
			    , costHeap.toString());
		
		assertEquals("Highway[city1=1, city2=0, cost=5.0, asphalt=99.0]", costHeap.deleteMin().toString());
		
		assertEquals("Heap[\n" + 
			    "   Highway[city1=1, city2=2, cost=6.0, asphalt=72.0],\n" + 
			    "   Highway[city1=2, city2=0, cost=7.0, asphalt=159.0],\n" + 
			    "   Highway[city1=0, city2=3, cost=14.0, asphalt=415.0],\n" +
			    "   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0],\n" + 
			    "   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0]\n]"
			    , costHeap.toString());

		assertEquals("Heap[\n" +
			    "   Highway[city1=1, city2=2, cost=6.0, asphalt=72.0],\n" + 
			    "   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0],\n" + 
				"   Highway[city1=1, city2=0, cost=5.0, asphalt=99.0],\n" + 
			    "   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0],\n" + 
			    "   Highway[city1=2, city2=0, cost=7.0, asphalt=159.0],\n" + 
			    "   Highway[city1=0, city2=3, cost=14.0, asphalt=415.0]\n]"
			    , asphaltHeap.toString());
		
		assertEquals("Highway[city1=1, city2=2, cost=6.0, asphalt=72.0]", asphaltHeap.deleteMin().toString());
		
		assertEquals("Heap[\n" +
				"   Highway[city1=1, city2=0, cost=5.0, asphalt=99.0],\n" + 
			    "   Highway[city1=3, city2=1, cost=10.0, asphalt=112.0],\n" + 
			    "   Highway[city1=0, city2=3, cost=14.0, asphalt=415.0],\n" + 
			    "   Highway[city1=3, city2=2, cost=12.0, asphalt=212.0],\n" + 
			    "   Highway[city1=2, city2=0, cost=7.0, asphalt=159.0]\n]" 
			    , asphaltHeap.toString());

		assertEquals(5, costHeap.size());
		assertEquals(5, asphaltHeap.size());
		assertEquals("COST", costHeap.getType());
		assertEquals("ASPHALT", asphaltHeap.getType());
		
		
	}

}
