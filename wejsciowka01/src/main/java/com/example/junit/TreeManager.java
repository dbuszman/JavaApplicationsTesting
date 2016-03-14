package com.example.junit;

import java.util.List;

public class TreeManager {
	
	private IMyList myList;
	
	public TreeManager(IMyList myList) {
		this.myList = myList;
	}
	
	public boolean testAdding(Tree tree){
		return myList.addTree(tree);
	}
	
	public boolean testRemoving(Tree tree){
		return myList.removeTree(tree);
	}
	public List<Tree> testGettingAllList(){
		return myList.getAll();
	}

}
