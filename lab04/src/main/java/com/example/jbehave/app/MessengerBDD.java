package com.example.jbehave.app;

import com.example.jbehave.messengerBDD.MalformedRecipientException;
import com.example.jbehave.messengerBDD.MessageService;
import com.example.jbehave.messengerBDD.SendingStatus;

public class MessengerBDD {

	private MessageService ms;

	public MessengerBDD(MessageService ms) {
		this.ms = ms;
	}
	
	private String server;
	private String message;
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int testConnection() {
		switch (ms.checkConnection(server)) {
		case FAILURE:
			return 1;
		case SUCCESS:
			return 0;
		}
		return 1;
	}

	public int sendMessage() {

		int result = -1;

		try {
			SendingStatus sStatus = ms.send(server, message);
			switch (sStatus) {
			case SENT:
				result = 0;
				break;
			case SENDING_ERROR:
				result = 1;
				break;
			default:
				result = -1;
			}

		} catch (MalformedRecipientException e) {
			result = 2;
		}
		return result;
	}
}
