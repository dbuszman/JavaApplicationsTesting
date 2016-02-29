package com.example.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TreeManagerTest {
	

	TreeManager treeManager = new TreeManager();
	
	private final static String NAME_1 = "Jablon";
	private final static String TYPE_1 = "lisciaste";
	private final static int AMOUNT_1 = 5;

	@Test
	public void checkAdding(){

		int treeSizeBeforeAdd = treeManager.trees.size();
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		treeManager.addTree(drzewo);
		
		int treesAmountShouldBeAfterAdd = treeSizeBeforeAdd + 1;
		
		int treeSizeAfterAdd = treeManager.trees.size();
		
		int treesSub = treeSizeAfterAdd - treeSizeBeforeAdd;
		
		assertEquals(treesAmountShouldBeAfterAdd, treesSub);
		
		assertEquals(NAME_1, treeManager.trees.get(treeManager.trees.size() - 1).getName());
		assertEquals(TYPE_1, treeManager.trees.get(treeManager.trees.size() - 1).getType());
		assertEquals(AMOUNT_1, treeManager.trees.get(treeManager.trees.size() - 1).getAmount());
		
	}
	
	@Test
	public void checkRemoving(){
		
		int treeSizeBeforeAdd = treeManager.trees.size();
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		treeManager.addTree(drzewo);
		
		int treesAmountShouldBeAfterAdd = treeSizeBeforeAdd + 1;
		
		int treeSizeAfterAdd = treeManager.trees.size();
		
		int treesSub = treeSizeAfterAdd - treeSizeBeforeAdd;
		
		assertEquals(treesAmountShouldBeAfterAdd, treesSub);
		
		treeManager.removeTree(drzewo);
		
		int currentShouldBeAmount = treesSub - 1;
		
		int treeSizeAfterRemove = treeManager.trees.size();
		
		assertEquals(currentShouldBeAmount, treeSizeAfterRemove);
		
	}
	
}
