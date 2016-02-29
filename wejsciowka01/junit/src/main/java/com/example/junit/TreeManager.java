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
	
	public List<Tree> getAllTrees(){
		
		List<Tree> positions = new ArrayList<Tree>();
		
		for (int i = 0; i < trees.size(); i++){
			
			Tree t = new Tree();
			t.setName(trees.get(i).getName());
			t.setType(trees.get(i).getType());
			t.setAmount(trees.get(i).getAmount());
			positions.add(t);
		}
		
		return positions;
	}
	
}
