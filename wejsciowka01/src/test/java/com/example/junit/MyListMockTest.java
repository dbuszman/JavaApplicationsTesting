package com.example.junit;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class MyListMockTest {

	
	private final static String NAME_1 = "Jablon";
	private final static String TYPE_1 = "lisciaste";
	private final static int AMOUNT_1 = 5;
	
	private TreeManager treeManager;
	private IMyList mock;

	@Before
	public void setUp() {
		mock = createMock(IMyList.class);
		treeManager = new TreeManager(mock);
	}

	@Test
	public void addingCheck() {
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(drzewo);
		
		expect(mock.addTree(drzewo)).andReturn(true).atLeastOnce();
				
		expect(mock.removeTree(drzewo)).andReturn(true).atLeastOnce();
		
		expect(mock.getAll()).andReturn(trees).anyTimes();
		
		replay(mock);
		
		assertEquals(true, treeManager.testAdding(drzewo));

		assertEquals(true, treeManager.testRemoving(drzewo));
		
		assertEquals(trees, treeManager.testGettingAllList());
		
		verify(mock);
	}
}
