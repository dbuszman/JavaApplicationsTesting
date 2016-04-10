package com.example.mockdemo.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*; 
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.mockdemo.messenger.ConnectionStatus;
import com.example.mockdemo.messenger.MalformedRecipientException;
import com.example.mockdemo.messenger.MessageService;
import com.example.mockdemo.messenger.SendingStatus;

public class MockitoMessageAppTest {

	private static final String VALID_SERVER = "inf.ug.edu.pl";
	private static final String INVALID_SERVER = "inf.ug.edu.eu";
	private static final String INVALID_SERVER_TOO_SHORT = "edu";


	private static final String VALID_MESSAGE = "some message";
	private static final String INVALID_MESSAGE = "ab";
	
	@Mock
	MessageService ms;
	private Messenger messenger;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		messenger = new Messenger(ms);
	}

	@After
	public void tearDown() throws Exception {
		messenger = null;
		ms = null;
	}

	@Test
	public void testConnectionValidSrv() {
		when(ms.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
		int respond = messenger.testConnection(VALID_SERVER);
		assertThat(respond, is(0));
		verify(ms).checkConnection(VALID_SERVER);
	}

	@Test
	public void testConnectionValidSrvNoConnect() {
		when(ms.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
		int respond = messenger.testConnection(VALID_SERVER);
		assertThat(respond, is(1));
		verify(ms).checkConnection(VALID_SERVER);
	}
	
	@Test
	public void testConnectionInvalidSrv() {
		when(ms.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
		int respond = messenger.testConnection(INVALID_SERVER);
		assertThat(respond, is(1));
		verify(ms).checkConnection(INVALID_SERVER);
	}
	
	@Test
	public void testConnectionNoSrv() {
		when(ms.checkConnection(null)).thenReturn(ConnectionStatus.FAILURE);
		int respond = messenger.testConnection(null);
		assertThat(respond, is(1));
		verify(ms).checkConnection(null);
	}
	
	@Test
	public void checkSendingMessageValidBothSent() throws MalformedRecipientException {
		when(ms.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
		int respond = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(respond, is(0));
		verify(ms).send(VALID_SERVER, VALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageValidBothSentFail() throws MalformedRecipientException {
		when(ms.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
		int respond = messenger.sendMessage(VALID_SERVER, VALID_MESSAGE);
		assertThat(respond, is(1));
		verify(ms).send(VALID_SERVER, VALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageValidSrvInvalidMsg() throws MalformedRecipientException {
		when(ms.send(VALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(ms).send(VALID_SERVER, INVALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageValidSrvNoMsg() throws MalformedRecipientException {
		when(ms.send(VALID_SERVER, null)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(VALID_SERVER, null);
		assertThat(respond, is(2));
		verify(ms).send(VALID_SERVER, null);
	}
	
	@Test
	public void checkSendingMessageInvalidSrvValidMsg() throws MalformedRecipientException {
		when(ms.send(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE);
		assertThat(respond, is(2));
		verify(ms).send(INVALID_SERVER_TOO_SHORT, VALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageInvalidBoth() throws MalformedRecipientException {
		when(ms.send(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(ms).send(INVALID_SERVER_TOO_SHORT, INVALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageInvalidSrvNoMsg() throws MalformedRecipientException {
		when(ms.send(INVALID_SERVER_TOO_SHORT, null)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(INVALID_SERVER_TOO_SHORT, null);
		assertThat(respond, is(2));
		verify(ms).send(INVALID_SERVER_TOO_SHORT, null);
	}
	
	@Test
	public void checkSendingMessageNoSrvValidMsg() throws MalformedRecipientException {
		when(ms.send(null, VALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(null, VALID_MESSAGE);
		assertThat(respond, is(2));
		verify(ms).send(null, VALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageNoSrvInvalidMsg() throws MalformedRecipientException {
		when(ms.send(null, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(null, INVALID_MESSAGE);
		assertThat(respond, is(2));
		verify(ms).send(null, INVALID_MESSAGE);
	}
	
	@Test
	public void checkSendingMessageNoSrvNoMsg() throws MalformedRecipientException {
		when(ms.send(null, null)).thenThrow(new MalformedRecipientException());
		int respond = messenger.sendMessage(null, null);
		assertThat(respond, is(2));
		verify(ms).send(null, null);
	}
}