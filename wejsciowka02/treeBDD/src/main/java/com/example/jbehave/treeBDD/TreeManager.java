package com.example.jbehave.treeBDD;

import java.util.ArrayList;
import java.util.List;

import com.example.jbehave.treeBDD.Tree;


public class TreeManager {

	List<Tree> trees = new ArrayList<Tree>();


	public void addTree(Tree tree){

		trees.add(tree);

	}

	public void removeTree(Tree tree){

		trees.remove(tree);

	}
	
	public List<Tree> getAllList(){
		return trees;
	}
	
	public List<Tree> findByName(String name){
		List<Tree> treesByName = new ArrayList<Tree>();
		
		for (Tree temp : trees) {
			if(temp.getName().equals(name)){
				treesByName.add(temp);
			}
		}
		
		return treesByName;
	}
	
	public int countTrees(){
		
		return trees.size();
		
	}
	


}
