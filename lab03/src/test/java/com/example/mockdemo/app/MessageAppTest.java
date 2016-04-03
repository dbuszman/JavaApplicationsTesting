package com.example.mockdemo.app;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.example.mockdemo.messenger.MessageServiceSimpleImpl;

public class MessageAppTest {

	private MessageServiceSimpleImpl mssi;
	private Messenger messenger;
	
	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";
	private final String INVALID_SERVER_TOO_SHORT = "edu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";

	@Before
	public void setUp() throws Exception {
		mssi = new MessageServiceSimpleImpl();
		messenger = new Messenger(mssi);
	}
	
	@After
	public void tearDown() throws Exception {
		mssi = null;
		messenger = null;
	}
	
	@Test
	public void checkSendingMessage_ValidSrvInvalidMsg() {
		assertEquals(2, messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE));	
	}
	
	@Test
	public void checkSendingMessage_InvalidSrvValidMsg() {
		assertEquals(2, messenger.sendMessage(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE));
	}
	
	@Test
	public void checkSendingMessage_InvalidBoth() {
		assertEquals(2, messenger.sendMessage(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE));
	}
	
	@Test
	public void checkSendingMessage_ValidSrvNoMsg() {
		assertEquals(2, messenger.sendMessage(VALID_SERVER, null));
	}
	
	@Test
	public void checkSendingMessage_InvalidSrvNoMsg() {
		assertEquals(2, messenger.sendMessage(INVALID_SERVER, null));
	}
	
	@Test
	public void checkSendingMessage_NoSrvValidMsg() {
		assertEquals(2, messenger.sendMessage(null, VALID_MESSAGE));
	}
	
	@Test
	public void checkSendingMessage_NoSrvInvalidMsg() {
		assertEquals(2, messenger.sendMessage(null, INVALID_MESSAGE));
	}
	
	@Test
	public void checkSendingMessage_NoSrvNoMsg() {
		assertEquals(2, messenger.sendMessage(null, null));
	}


}
