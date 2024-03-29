package com.example.junit;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MyListMockTest {

	
	private final static String NAME_1 = "Jablon";
	private final static String TYPE_1 = "lisciaste";
	private final static int AMOUNT_1 = 5;
	
	private final static String NAME_2 = "Wiśnia";
	private final static String TYPE_2 = "lisciaste";
	private final static int AMOUNT_2 = 5;
	
	private final static String WRONG_NAME = "Świerk";	
	
	private List<Tree> trees;
	
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
		assertEquals(true, treeManager.adding(drzewo));
		verify(mock);
	}
	
	@Test
	public void removingCheck() {	
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);

		expect(mock.removeTree(drzewo)).andReturn(true).atLeastOnce();
		replay(mock);
		assertEquals(true, treeManager.removing(drzewo));
		verify(mock);
	}
	
	@Test
	public void gettingAllCheck() {	
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		List<Tree> trees = new ArrayList<Tree>();
		trees.add(drzewo);
		
		expect(mock.getAll()).andReturn(trees).atLeastOnce();
		replay(mock);
		assertEquals(trees, treeManager.gettingAllList());
		verify(mock);
	}
	
	@Test
	public void findingByNameCheck() {	
		
		Tree drzewo1 = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		Tree drzewo2 = new Tree(NAME_1, TYPE_2, AMOUNT_2);
		
		trees.add(drzewo1);
		trees.add(drzewo2);
		
		expect(mock.findByName(NAME_2)).andReturn(trees).atLeastOnce();
		replay(mock);
		assertEquals(trees, treeManager.findByName(NAME_2));
		verify(mock);
	}
	
	@Test
	public void findingByNameNotExistCheck() {	
		
		expect(mock.findByName(WRONG_NAME)).andReturn(null).atLeastOnce();
		replay(mock);
		assertEquals(null, treeManager.findByName(WRONG_NAME));
		verify(mock);
	}
	
	@Test
	public void findingByAmountCheck() {	
		
		Tree drzewo1 = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		Tree drzewo2 = new Tree(NAME_2, TYPE_2, AMOUNT_1);
		
		trees.add(drzewo1);
		trees.add(drzewo2);
		
		expect(mock.findByAmount(AMOUNT_1)).andReturn(trees).atLeastOnce();
		replay(mock);
		assertEquals(trees, treeManager.findByAmount(AMOUNT_1));
		verify(mock);
	}
	
	
}
