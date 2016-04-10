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
	public void testConnectionValidSrv() {
		expect(mock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(VALID_SERVER);
		assertThat(respond, is(0));
		verify(mock);
	}
	
	@Test
	public void testConnectionValidSrvNoConnect() {
		expect(mock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.FAILURE).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(VALID_SERVER);
		assertThat(respond, is(1));
		verify(mock);
	}

	@Test
	public void testConnectionInvalidSrv() {
		expect(mock.checkConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(INVALID_SERVER);
		assertThat(respond, is(1));
		verify(mock);
	}
	
	@Test
	public void testConnectionNoSrv() {
		expect(mock.checkConnection(null)).andReturn(ConnectionStatus.FAILURE).atLeastOnce();
		replay(mock);
		int respond = messenger.testConnection(null);
		assertThat(respond, is(1));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageValidBothSent() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT).atLeastOnce();
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(respond, is(0));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageValidBothSentFail() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR).atLeastOnce();
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(respond, is(1));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageValidSrvInvalidMsg() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageValidSrvNoMsg() throws MalformedRecipientException {
		expect(mock.send(VALID_SERVER, null)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(VALID_SERVER, null);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageInvalidSrvValidMsg() throws MalformedRecipientException {
		expect(mock.send(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageInvalidBoth() throws MalformedRecipientException {
		expect(mock.send(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageInvalidSrvNoMsg() throws MalformedRecipientException {
		expect(mock.send(INVALID_SERVER_TOO_SHORT, null)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, null);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageNoSrvValidMsg() throws MalformedRecipientException {
		expect(mock.send(null, VALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(null, VALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageNoSrvInvalidMsg() throws MalformedRecipientException {
		expect(mock.send(null, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(null, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(mock);
	}
	
	@Test
	public void checkSendingMessageNoSrvNoMsg() throws MalformedRecipientException {
		expect(mock.send(null, null)).andThrow(new MalformedRecipientException());
		replay(mock);
		int respond = messenger.sendMessage(null, null);
		assertThat(respond, is(2));
		verify(mock);
	}

}
