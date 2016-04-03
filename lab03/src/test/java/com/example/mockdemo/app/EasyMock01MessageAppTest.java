package com.example.mockdemo.app;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

public class EasyMock01MessageAppTest {
	
	private static final String VALID_SERVER = "inf.ug.edu.pl";
	private static final String INVALID_SERVER = "inf.ug.edu.eu";
	private static final String INVALID_SERVER_TOO_SHORT = "edu";


	private static final String VALID_MESSAGE = "some message";
	private static final String INVALID_MESSAGE = "ab";
	
	private Messenger messenger;
	private MessageService mock;
	
	@Before
	public void setUp() {
		mock = createMock(MessageService.class);
		messenger = new Messenger(mock);
	}
	
	@After
	public void tearDown() throws Exception {
		messenger = null;
		mock = null;
	}

	@Test
	public void testConnection_ValidSrv() {
		expect(mock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(VALID_SERVER);
		assertThat(respond, is(0));
		verify(mock);
	}
	
	@Test
	public void testConnection_ValidSrvNoConnect() {
		expect(mock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.FAILURE).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(VALID_SERVER);
		assertThat(respond, is(1));
		verify(mock);
	}

	@Test
	public void testConnection_InvalidSrv() {
		expect(mock.checkConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(INVALID_SERVER);
		assertThat(respond, is(1));
		verify(mock);
	}
	
	@Test
	public void testConnection_NoSrv() {
		expect(mock.checkConnection(null)).andReturn(ConnectionStatus.FAILURE).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(null);
		assertThat(respond, is(1));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_ValidBothSent() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT).atLeastOnce();
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(respond, is(0));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_ValidBothSentFail() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR).atLeastOnce();
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(respond, is(1));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_ValidSrvInvalidMsg() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_ValidSrvNoMsg() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, null)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, null);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_InvalidSrvValidMsg() throws MalformedRecipientException {
		expect(mock.send(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_InvalidBoth() throws MalformedRecipientException {
		expect(mock.send(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_InvalidSrvNoMsg() throws MalformedRecipientException {
		expect(mock.send(INVALID_SERVER_TOO_SHORT, null)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, null);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_NoSrvValidMsg() throws MalformedRecipientException {
		expect(mock.send(null, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(null, VALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_NoSrvInvalidMsg() throws MalformedRecipientException {
		expect(mock.send(null, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(null, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessage_NoSrvNoMsg() throws MalformedRecipientException {
		expect(mock.send(null, null)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(null, null);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSending() throws MalformedRecipientException {
		expect(mock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS).anyTimes();
		expect(mock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT).atLeastOnce();
		replay(mock);
		assertThat(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE),
				either(equalTo(0)).or(equalTo(1)));
		verify(mock);
	}

}
