package com.example.junit;

import java.util.List;

public interface IMyList {
	
	boolean addTree(Tree tree);
	boolean removeTree(Tree tree);
	List<Tree> getAll();
	List<Tree> findByName(String name);
	List<Tree> findByAmount(int amount);
}
