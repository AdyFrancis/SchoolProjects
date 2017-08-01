package edu.ncsu.csc316.transportation_manager.highway;


/**
 * This class represents a Highway object
 * @author Ady
 *
 */
public class Highway {
	
	private int city1;
	private int city2;
	private double cost;
	private double asphalt; 
	
	/**
	 * Constructs a Highway with the given information
	 * @param city1 city1 of the highway
	 * @param city2 city2 of the highway
	 * @param cost cost of building the highway
	 * @param asphalt amount (in miles) of asphalt needed to build the highway
	 */
	public Highway(int city1, int city2, double cost, double asphalt) {
		this.city1 = city1;
		this.city2 = city2;
		this.cost = cost;
		this.asphalt = asphalt;
	}
	
	/**
	 * Returns city 1 of the highway.
	 * @return city 1
	 */
	public int getCity1(){
		return city1;
	}

	/**
	 * Returns city 2 of the highway.
	 * @return city 2
	 */
	public int getCity2(){
		return city2;
	}
	
	/**
	 * Returns the cost of the highway.
	 * @return the cost
	 */
	public double getCost(){
		return cost;
	}
	
	/**
	 * Returns the asphalt amount of the highway.
	 * @return the asphalt
	 */
	public double getAsphalt(){
		return asphalt;
	}
	
	/**
	 * Returns a string representation of the Highway
	 * in the format:
	 * 
	 * Highway[city1=X, city2=X, cost=X.X, asphalt=X.X]
	 * 
	 * @return the string representation of the highway
	 */
	@Override
	public String toString() {
		return "Highway[city1=" + city1 + ", city2=" + city2 + ", cost=" + cost + ", asphalt=" + asphalt + "]";
	}

}
