package edu.ncsu.csc316.ancestrytree.trees;

import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;
import edu.ncsu.csc316.ancestrytree.data.AhnentafelEntry;


/**
 * This class represents the Ancestry tree given
 * an ahnentafel input file.
 * @author Ady Francis
 *
 */
public class AhnentafelTree implements AncestryTree {
	
	private MultiPurposeList<AhnentafelEntry> list;
	private AhnentafelEntry root;
	
	/**
	 * Parameterized constructor 
	 * @param list the MultiPurposeList that represents the tree
	 */
	public AhnentafelTree(MultiPurposeList<AhnentafelEntry> list) {
		this.list = list;
		setRoot(list.get(0));
	}
	
	/**
	 * This method returns the string representation
	 * of the level order traversal of the Ahnentafel Tree.
	 * @return string representation of level order traversal
	 */
	public String levelOrderTraversal() {
		
		if (list.size() == 1){
			return root.toString();
		}
		
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			AhnentafelEntry ae = list.get(i);
			if (ae != null) {
				if (i > 0)
					str.append("; ");
				
				str.append(ae.toString());
			}
		}
		return str.toString();
	}
	
	/**
	 * Sets the root of the Ahnentafel Tree
	 * @param a the data that will be held in the root node
	 */
	private void setRoot(AhnentafelEntry a) {
		this.root = a;
	}
	
	/**
	 * This method returns the root
	 * @return the root of the tree
	 */
	public AhnentafelEntry getRoot() {
		return root;
	}
	
	/**
	 * Returns the relationship between the root node
	 * and the name of the ancestor specified.
	 * @param name name of the ancestor to find relationship between
	 * @return the relationship between root node and the ancestor
	 */
	public String getRelationship (String name) {
		AhnentafelEntry entry = this.getChild(name);
		if (entry == null)
			return name + " was not found in the tree.";
		
	
		String ret = name + " is " + root.getName() + "'s ";
		String rel = "";
		int num = entry.getNum();
		
		int level = (int)(Math.log(num) / Math.log(2));

		if (num == 1)
			return name + " is " + name;
		
		if (num % 2 == 0)
			rel += "father";
		else 
			rel += "mother";
		if (level < 2)
			return ret + rel;
		
		for (int i = 0; i <= level; i++) {
			if (i == 2)
				rel = "grand" + rel;
			if (i > 2)
				rel = "great-" + rel;
		}
	    return ret + rel;
	}
	
	/**
	 * Finds and returns the child with the specified name.
	 * Returns null if the child is not found
	 * @param name name of the ahnentafel entry
	 * @return ahnentafel entry with the specified name
	 */
	public AhnentafelEntry getChild(String name) {
		for (int i = 0; i < list.size(); i++) {
			AhnentafelEntry a = list.get(i);
			if (a != null && a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	

}
