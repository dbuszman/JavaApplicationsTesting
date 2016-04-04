package com.example.junit;

import java.util.List;

public interface IMyList {
	
	boolean addTree(Tree tree);
	boolean removeTree(Tree tree);
	List<Tree> getAll();
	Tree findByNameAtList(String name, List<Tree> trees);
	List<Tree> findByAmountAtList(int amount, List<Tree> trees);
}
