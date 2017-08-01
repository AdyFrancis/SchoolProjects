package edu.ncsu.csc316.ancestrytree.manager;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.ncsu.csc316.ancestrytree.data.AhnentafelEntry;
import edu.ncsu.csc316.ancestrytree.data.Person;
import edu.ncsu.csc316.ancestrytree.io.AhnentafelFileReader;
import edu.ncsu.csc316.ancestrytree.io.TraversalFileReader;
import edu.ncsu.csc316.ancestrytree.list.MultiPurposeList;
import edu.ncsu.csc316.ancestrytree.trees.AhnentafelTree;
import edu.ncsu.csc316.ancestrytree.trees.AncestryTree;
import edu.ncsu.csc316.ancestrytree.trees.TraversalTree;
import edu.ncsu.csc316.ancestrytree.data.Node;

/**
 * This class builds the trees and outputs the LevelOrderTraversal
 * or determines the relationship
 * @author Ady Francis
 *
 */
public class AncestryTreeManager {
	
		MultiPurposeList<Person> preOrder;
		MultiPurposeList<Person> postOrder;
		MultiPurposeList<AhnentafelEntry> ahn;
		AncestryTree ancestryTree;
		static JFrame frame = null;
		
	/**
	 * Constructor to initialize an instance of your AncestryTreeManager object
	 * @param preOrderFilePath the path to the file that contains the preOrder traversal
	 * @param postOrderFilePath the path to the file that contains the postOrder traversal
	 */
	public AncestryTreeManager(String preOrderFilePath, String postOrderFilePath) {
		TraversalFileReader preReader = null;
		TraversalFileReader postReader = null;
	
		try {
			 preReader = new TraversalFileReader(preOrderFilePath);
			 postReader = new TraversalFileReader(postOrderFilePath);
		}
		catch (FileNotFoundException fne){
			JOptionPane.showMessageDialog(frame, preOrderFilePath + " or " + postOrderFilePath + "(The system cannot find the file specified.)");
			System.exit(1);
		}
		
	    preOrder = preReader.getList();
	    postOrder = postReader.getList();
	    
	    TraversalTree tree = new TraversalTree();
	    tree.setRoot(buildTree(preOrder, 0, preOrder.size() - 1, postOrder, 0, postOrder.size() - 1));
	    ancestryTree = tree;
	}

	/**
	 * Constructor to initialize an instance of your AncestryTreeManager object
	 * @param ahnentafelFilePath the path to the file that contains the ahnentafel
	 */
	public AncestryTreeManager(String ahnentafelFilePath) {
		AhnentafelFileReader aReader = null;
		try {
			aReader = new AhnentafelFileReader(ahnentafelFilePath);
		}
		catch (FileNotFoundException fne){
			JOptionPane.showMessageDialog(frame, ahnentafelFilePath + "(The system cannot find the file specified.)");
			System.exit(1);
		}
	    ahn = aReader.getList();
	    this.ancestryTree = (AhnentafelTree)buildTree(ahn);
	}

	/**
	 * Returns the level-order traversal for the tree
	 * as a String in the format:
	 * LevelOrder[lastName, firstName; lastName2, firstName2]
	 * @return the level-order traversal for the tree
	 */
	public String getLevelOrder() {
	    return "LevelOrder[" + ancestryTree.levelOrderTraversal() + "]";
	}

	/**
	 * Returns a description of the relationship between
	 * two individuals in the tree, formatted as:
	 * [nameA] is [nameB]'s [relationship]
	 * @param nameA the first person to find in the tree
	 * @param nameB the second person to find in the tree
	 * @return a description of how the two people are related
	 */
	public String getRelationship(String nameA, String nameB) {
		TraversalTree relTree = (TraversalTree)ancestryTree;
		return relTree.getRelationship(nameA, nameB);
	}

	/**
	 * Returns a description of the relationship between
	 * two individuals in the tree, formatted as:
	 * [name] is [root]'s [relationship]
	 * @param name the person to find in the tree
	 * @return a description of how the person is related to the person at the root of the tree
	 */
	public String getRelationship(String name) {
		AhnentafelTree relTree = (AhnentafelTree)ancestryTree;
		return relTree.getRelationship(name);
	}
	/**
	 * Builds the TraversalTree given a pre-order and post-order list
	 * @param tree the tree that will be constructed
	 * @param preOrder the pre-order list of entries
	 * @param preMin minimum index of the preOrder list
	 * @param preMax max index of the preOrder list
	 * @param postOrder the post order list of entries
	 * @param postMin the minimum index of the postOrder list
	 * @param postMax the maximum index of the postOrder list
	 * @return returns the built traversal tree
	 */
	private Node buildTree( MultiPurposeList<Person> preOrder, int preMin, int preMax, MultiPurposeList<Person> postOrder, int postMin, int postMax) {

		int preCursor = preMin + 1;
		int postCursor = postMin;
		Node root = new Node(preOrder.get(preMin));
		
		while (preCursor <= preMax) {
			int shift = 0;
			
			preMin = preCursor;
			postMin = postCursor;
			
			while (!(postOrder.get(postCursor + shift)).equals(preOrder.get(preCursor)))
				shift++;
			
			Node child = buildTree(preOrder, preMin, preMax, postOrder, postMin, postMax);
			root.addChild(child);
			shift++;
			preMax = 0;
			postMax  = 0;
		}
		
		return root;
	}
	
	
	/**
	 * Creates an instance of the Ahnentafel Tree
	 * @param list the list of entries inside the tree
	 * @return an instance of the Ahnentafel tree
	 */
	private AhnentafelTree buildTree(MultiPurposeList<AhnentafelEntry> list) {
		AhnentafelTree tree = new AhnentafelTree(list);
		
		return tree;
	}
	
	/**
	 * Returns an instance of the tree that was build
	 * @return an instance of the built tree
	 */
	public AncestryTree getTree() {
		return ancestryTree;
	}
	


}
