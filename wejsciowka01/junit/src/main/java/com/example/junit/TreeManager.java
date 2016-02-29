package com.example.junit;

import java.util.ArrayList;
import java.util.List;

public class TreeManager {

	List<Tree> trees = new ArrayList<Tree>();
		
	
	public void addTree(Tree tree){
		
		trees.add(tree);
		
	}
	
	public void removeTree(Tree tree){
		
		trees.remove(tree);
		
	}
	
}
