/**
 * 
 */
package edu.ncsu.csc316.compression_manager.data;

/**
 * @author Ady Francis
 *
 */
public class Token {
	
	
	private String str;
	/**
	 * Type of token
	 * 0 - Space
	 * 1 - String
	 * 2 - Special
	 */
	private int type;
	
	public Token (String str, int type){
		
		this.str = str;
		this.type = type;
	}
	
	public String getContent(){
		return this.str;
	}
	
	public int getType(){
		return this.type;
	}
	
	public String toString(){
		return getContent();
	}

}
