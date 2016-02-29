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

		int treeSizeBefore = treeManager.trees.size();
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		treeManager.addTree(drzewo);
		
		int treeSizeAfter = treeManager.trees.size();
		
		int treesSub = treeSizeAfter - treeSizeBefore;
		
		assertEquals(1, treesSub);
		
		assertEquals(NAME_1, treeManager.trees.get(treeManager.trees.size() - 1).getName());
		assertEquals(TYPE_1, treeManager.trees.get(treeManager.trees.size() - 1).getType());
		assertEquals(AMOUNT_1, treeManager.trees.get(treeManager.trees.size() - 1).getAmount());
		
	}
	
	@Test
	public void checkRemoving(){
		
	}
	
}
