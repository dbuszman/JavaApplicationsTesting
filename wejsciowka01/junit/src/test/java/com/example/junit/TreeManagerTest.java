package com.example.junit;

import org.junit.Test;

public class TreeManagerTest {
	

	TreeManager treeManager = new TreeManager();

	@Test
	public void checkAdding(){

		Tree drzewo = new Tree("Jablon", "lisciaste", 2);
		
		treeManager.addTree(drzewo);
	}
	
}
