package com.example.junit;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MyListMockTest {

	
	private final static String NAME_1 = "Jablon";
	private final static String TYPE_1 = "lisciaste";
	private final static int AMOUNT_1 = 5;
	
	private MyList myList;
	private ITreeManager mock;

	@Before
	public void setUp() {
		mock = createMock(ITreeManager.class);
		myList = new MyList(mock);
	}

	@Test
	public void addingCheck() {
		
		Tree drzewo = new Tree(NAME_1, TYPE_1, AMOUNT_1);
		
		expect(mock.addTree(drzewo)).andReturn(true).atLeastOnce();
				
		expect(mock.removeTree(drzewo)).andReturn(true).atLeastOnce();
		replay(mock);
		
		assertEquals(true, myList.testAdding(drzewo));

		assertEquals(true, myList.testRemoving(drzewo));
		verify(mock);
	}
}
