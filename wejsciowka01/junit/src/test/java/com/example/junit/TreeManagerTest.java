package com.example.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TreeManagerTest {
	

	TreeManager treeManager = new TreeManager();
	
	private final static String NAME_1 = "Jablon";
	private final static String TYPE_1 = "lisciaste";
	private final static int AMOUNT_1 = 5;

	@Test
	public void checkAdding(){

		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		treeManager.addTree(drzewo);
		
		assertEquals(NAME_1, treeManager.trees.get(0).getName());
		assertEquals(TYPE_1, treeManager.trees.get(0).getType());
		assertEquals(AMOUNT_1, treeManager.trees.get(0).getAmount());
		
	}
	
}
