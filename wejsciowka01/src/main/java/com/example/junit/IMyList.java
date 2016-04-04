package com.example.junit;

import java.util.List;

public interface IMyList {
	
	boolean addTree(Tree tree);
	boolean removeTree(Tree tree);
	List<Tree> getAll();
	Tree findByName(String name, List<Tree> trees);
	List<Tree> findByAmount(int amount, List<Tree> trees);
}
