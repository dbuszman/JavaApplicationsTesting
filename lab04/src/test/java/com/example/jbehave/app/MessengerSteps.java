package com.example.jbehave.app;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.example.jbehave.app.MessengerBDD;
import com.example.jbehave.messengerBDD.MessageServiceSimpleImpl;

public class MessengerSteps {
	
	private MessageServiceSimpleImpl mssi;
	private MessengerBDD messenger;
	
	@Given("a messenger")
	public void messengerSetup(){
		mssi = new MessageServiceSimpleImpl();
		messenger = new MessengerBDD(mssi);
	}
	
	@When("set server to $server and message to $message")
	public void setArguments(String server, String message){
		if(server.equals("not set"))
			server = null;
		
		messenger.setServer(server);
		
		if(message.equals("not set"))
			message = null;
		
		messenger.setMessage(message);
	}
	
    @Then("sending message should be $result")
	public void shouldBe(int result){
		assertEquals(result, messenger.sendMessage());
	}
    
}
