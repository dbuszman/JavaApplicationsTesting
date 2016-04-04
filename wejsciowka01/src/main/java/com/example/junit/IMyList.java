package com.example.junit;

import java.util.List;

public interface IMyList {
	
	boolean addTree(Tree tree);
	boolean removeTree(Tree tree);
	List<Tree> getAll();
	Tree findByName(String name);
	Tree findByAmount(int amount);
}
