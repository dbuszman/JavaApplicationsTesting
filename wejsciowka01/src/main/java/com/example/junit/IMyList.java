package com.example.junit;

import java.util.List;

public interface IMyList {
	
	boolean addTree(Tree tree);
	boolean removeTree(Tree tree);
	public List<Tree> getAll();
}
