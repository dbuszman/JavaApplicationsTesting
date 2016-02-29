package com.example.junit;

import java.util.ArrayList;
import java.util.List;

public class TreeManager {
	
	List<Tree> trees = new ArrayList<Tree>();
		
	
	public void addTree(Tree tree){
		
		Tree drzewo = new Tree("Jablon", "lisciaste", 3);
		
		trees.add(drzewo);
	}
	
}
