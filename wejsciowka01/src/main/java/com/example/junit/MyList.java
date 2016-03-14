package com.example.junit;


public class MyList {
	
	private ITreeManager treeManager;
	
	public MyList(ITreeManager treeManager) {
		this.treeManager = treeManager;
	}
	
	public boolean testAdding(Tree tree){
		return treeManager.addTree(tree);
		
	}
	
	public boolean testRemoving(Tree tree){
		return treeManager.removeTree(tree);
	}
}
