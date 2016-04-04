package com.example.junit;

import java.util.List;

public class TreeManager {
	
	private IMyList myList;
	
	public TreeManager(IMyList myList) {
		this.myList = myList;
	}
	
	public boolean adding(Tree tree){
		return myList.addTree(tree);
	}
	
	public boolean removing(Tree tree){
		return myList.removeTree(tree);
	}
	public List<Tree> gettingAllList(){
		return myList.getAll();
	}
	
	public Tree findByNameAtList(String name, List<Tree> trees){
		return myList.findByNameAtList(name, trees);
	}
	
	public List<Tree> findByAmountAtList(int amount, List<Tree> trees){
		return myList.findByAmountAtList(amount, trees);
	}
}
