package com.example.junit;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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

	@After
	public void tearDown() throws Exception {
		treeManager = null;
		mock = null;
	}

	@Test
	public void addingCheck() {
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);

		expect(mock.addTree(drzewo)).andReturn(true).atLeastOnce();
		replay(mock);
		assertEquals(true, treeManager.testAdding(drzewo));
		verify(mock);
	}
	
	@Test
	public void removingCheck() {	
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);

		expect(mock.removeTree(drzewo)).andReturn(true).atLeastOnce();
		replay(mock);
		assertEquals(true, treeManager.testRemoving(drzewo));
		verify(mock);
	}
	
	@Test
	public void gettingAllCheck() {	
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(drzewo);
		
		expect(mock.getAll()).andReturn(trees).atLeastOnce();
		replay(mock);
		assertEquals(trees, treeManager.testGettingAllList());
		verify(mock);
	}
	
	@Test
	public void findingByNameCheck() {	
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(drzewo);
		
		expect(mock.findByName(NAME_1)).andReturn(drzewo).atLeastOnce();
		replay(mock);
		assertEquals(drzewo, treeManager.testFindByName(NAME_1));
		verify(mock);
	}
	
	@Test
	public void findingByAmount() {	
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(drzewo);
		
		expect(mock.findByAmount(AMOUNT_1)).andReturn(drzewo).atLeastOnce();
		replay(mock);
		assertEquals(drzewo, treeManager.testFindByAmount(AMOUNT_1));
		verify(mock);
	}
}
