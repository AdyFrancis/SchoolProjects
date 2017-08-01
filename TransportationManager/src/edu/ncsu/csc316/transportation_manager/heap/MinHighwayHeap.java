package edu.ncsu.csc316.transportation_manager.heap;

import edu.ncsu.csc316.transportation_manager.highway.Highway;
import edu.ncsu.csc316.transportation_manager.list.MultiPurposeList;


/**
 * Minimum heap sorted by the property passed into the constructor
 * @author Ady Francis
 *
 */
public class MinHighwayHeap {
	
	private String type;
	private MultiPurposeList<Highway> heap;

	/**
	* Constructs a new Highway heap
	* 
	* @param type the type of weight to consider ("COST" or "ASPHALT") when
	*         operating on the heap
	*/
	public MinHighwayHeap(String type) {
	    this.type = type;
	    heap = new MultiPurposeList<Highway>();
	}

	/**
	 * Inserts the given Highway into the minheap
	 * @param hwy the Highway to insert into the minheap
	 */
	public void insert(Highway hwy) {
		heap.add(hwy);
		upheap(size() - 1);
	}
	
	/**
	 * Preserves the min-heap property
	 * @param pos position of the node in the heap
	 */
	private void upheap(int pos) {
		
		if (type.equals("COST")){
			Highway c = heap.get(pos);
			Highway p = heap.get((pos - 1) / 2);
			if (pos > 0 && (p.getCost() > c.getCost())) {
				Highway temp = p;
				heap.set(c, (pos - 1) / 2);
				heap.set(temp, pos);
				upheap((pos - 1) / 2);
				
			}
		}
		else if (type.equals("ASPHALT")) {
			Highway c = heap.get(pos);
			Highway p = heap.get((pos - 1) / 2);
			if (pos > 0 && (p.getAsphalt() > c.getAsphalt())) {
				Highway temp = p;
				heap.set(c, (pos - 1) / 2);
				heap.set(temp, pos);
				upheap((pos - 1) / 2);
				
			}
		}
	}

	/**
	 * Goes down the min-heap to preserve heap-order property
	 * @param pos the position of the last added element
	 */
	private void downheap(int pos) {
		int i = 0;
		int left = (2 * pos) + 1;
		int right = (2 * pos) + 2;
		
		if (type.equals("COST")) {
			if (right < size()) {
					Highway leftC = heap.get(left);
					Highway rightC = heap.get(right);
					if (leftC.getCost() > rightC.getCost())
						i = right;
					else i = left;				
			}
			else if (left < size()) {
				i = left;
			}
			
			if (i > 0 && heap.get(pos).getCost() > heap.get(i).getCost()) {
				Highway a = heap.get(pos);
				Highway b  = heap.get(i);
				Highway temp = b;
				heap.set(a, i);
				heap.set(temp, pos);
				downheap(i);
			}
		}
		else if (type.equals("ASPHALT")) {
			if (right < size()) {
				Highway leftC = heap.get(left);
				Highway rightC = heap.get(right);
				if (leftC.getAsphalt() > rightC.getAsphalt())
					i = right;
				else i = left;				
		}
		else if (left < size()) {
			i = left;
		}
		
		if (i > 0 && heap.get(pos).getAsphalt() > heap.get(i).getAsphalt()) {
			Highway a = heap.get(pos);
			Highway b  = heap.get(i);
			Highway temp = b;
			heap.set(a, i);
			heap.set(temp, pos);
			downheap(i);
		}
		}
		
	}
	
	
	/**
	 * Returns the Highway with minimum weight in the minheap
	 * @return the Highway with minimum weight in the minheap
	 */
	public Highway deleteMin() {
		Highway min;
		
		if (size() > 0) {
			Highway a = heap.get(0);
			Highway b = heap.get(size() - 1);
			min = a;
			Highway temp = b;
			heap.set(a, size() - 1);
			heap.set(temp, 0);
			heap.remove(size() - 1);
		}
		else return null;
		
		downheap(0);
		
		return min;
	}
	
	/**
	 * The type the min-heap is sorted by
	 * @return the type of the min-heap
	 */
	public String getType() {
		return type;
	}

	/**
	 * The size of the min-heap
	 * @return the size of the min-heap
	 */
	public int size() {
		return heap.size();
	}

	/**
	 * Returns a string representation of the level-order traversal 
	 * of the heap in the following format:
	 * 
	 * Heap[
	 *    Highway[city1=X, city2=X, cost=X.X, asphalt=X.X],
	 *    Highway[city1=X, city2=X, cost=X.X, asphalt=X.X],
	 *    Highway[city1=X, city2=X, cost=X.X, asphalt=X.X]
	 * ]
	 *
	 * @return the string representation of the minheap
	 */
	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();
		str.append("Heap[\n");
		for (int i = 0; i < size(); i++) {
			str.append("   " + heap.get(i).toString());
			if (i < size() - 1)
				str.append(",");
			str.append("\n");
		}
		str.append("]");
		return str.toString();
	}
}
