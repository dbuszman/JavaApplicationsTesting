package com.example.jbehave.app;

import static org.junit.Assert.assertEquals;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.example.jbehave.app.MessengerBDD;
import com.example.jbehave.messengerBDD.MessageServiceSimpleImpl;

public class MessengerSteps {
	
	private MessageServiceSimpleImpl mssi;
	private MessengerBDD messenger;
	
	@AfterScenario(uponOutcome=AfterScenario.Outcome.SUCCESS)
	public void success() {
		System.out.println("\n\n Scenario test passed!");
	}
	
	@AfterScenario(uponOutcome=AfterScenario.Outcome.FAILURE)
	public void fail() {
		System.out.println("\n\n Something went wrong!");
	}
	
	@Given("a messenger")
	public void messengerSetup(){
		mssi = new MessageServiceSimpleImpl();
		messenger = new MessengerBDD(mssi);
	}
	
	@When("set server to: $server and message to: $message")
	public void setArguments(String server, String message){
		if(server.equals("not set"))
			server = null;
		
		messenger.setServer(server);
		
		if(message.equals("not set"))
			message = null;
		
		messenger.setMessage(message);
	}
	
	@When("set server as $server")
	public void setArgument(String server) {
		if(server.equals("not set"))
			server = null;
		
		messenger.setServer(server);
	}

	@Then("test connection should return $result")
	public void shouldConnectionReturn(int result) {
		assertEquals(result, messenger.testConnection());
	}

    @Then("sending message should be $result")
	public void shouldBe(int result){
		assertEquals(result, messenger.sendMessage());
    }

    @Then("sending message could be $result1 or $result2")
	public void shouldBeAlternative(int result1, int result2){
    	assertThat(messenger.sendMessage(), anyOf(is(result1), is(result2)));
	}

}
