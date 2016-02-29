package com.example.junit;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Test;

public class TreeManagerTest {
	

	TreeManager treeManager = new TreeManager();
	
	private final static String NAME_1 = "Jablon";
	private final static String TYPE_1 = "lisciaste";
	private final static int AMOUNT_1 = 5;

	@Test
	public void checkAdding(){
		
		List<Tree> positionsBefore = treeManager.getAllTrees();

		int treeSizeBeforeAdd = positionsBefore.size();
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		treeManager.addTree(drzewo);
		
		List<Tree> positionsAfter = treeManager.getAllTrees();
		
		int treesAmountShouldBeAfterAdd = treeSizeBeforeAdd + 1;
		int treeSizeAfterAdd = positionsAfter.size();
		int treesSub = treeSizeAfterAdd - treeSizeBeforeAdd;
		
		assertEquals(treesAmountShouldBeAfterAdd, treesSub);
		
		assertEquals(NAME_1, positionsAfter.get(positionsAfter.size() - 1).getName());
		assertEquals(TYPE_1, positionsAfter.get(positionsAfter.size() - 1).getType());
		assertEquals(AMOUNT_1, positionsAfter.get(positionsAfter.size() - 1).getAmount());
		
	}
	
	@Test
	public void checkRemoving(){
		
		List<Tree> positionsBefore = treeManager.getAllTrees();

		int treeSizeBeforeAdd = positionsBefore.size();
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		treeManager.addTree(drzewo);
		
		List<Tree> positionsAfterAdd = treeManager.getAllTrees();
		
		int treesAmountShouldBeAfterAdd = treeSizeBeforeAdd + 1;
		int treeSizeAfterAdd = positionsAfterAdd.size();
		int treesSub = treeSizeAfterAdd - treeSizeBeforeAdd;
		
		assertEquals(treesAmountShouldBeAfterAdd, treesSub);
		
		treeManager.removeTree(drzewo);
		
		List<Tree> positionsAfterRemove = treeManager.getAllTrees();
		
		int currentShouldBeAmount = treesSub - 1;
		int treeSizeAfterRemove = positionsAfterRemove.size();
		
		assertEquals(currentShouldBeAmount, treeSizeAfterRemove);
		
		assertEquals(positionsBefore, positionsAfterRemove);
	}
	
}
