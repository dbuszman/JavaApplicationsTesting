package com.example.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TreeManagerTest {
	

	TreeManager treeManager = new TreeManager();

	@Test
	public void checkAdding(){

		Tree drzewo = new Tree("Jablon", "lisciaste", 2);
		
		treeManager.addTree(drzewo);
		
		assertEquals(NAME_2, positionRetrieved.getName());
	}
	
}
