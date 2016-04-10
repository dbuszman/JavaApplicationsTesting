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
	
	public List<Tree> findByName(String name){
		return myList.findByName(name);
	}
	
	public List<Tree> findByAmount(int amount){
		return myList.findByAmount(amount);
	}
}
