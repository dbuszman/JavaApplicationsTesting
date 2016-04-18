package com.example.jbehave.treeBDD;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;


public class TreeManagerSteps {
	
	private TreeManager manager;
	private Tree tree;
	
	@AfterScenario(uponOutcome=AfterScenario.Outcome.SUCCESS)
	public void success() {
		System.out.println("\n\n Scenario test passed!");
	}
	
	@AfterScenario(uponOutcome=AfterScenario.Outcome.FAILURE)
	public void fail() {
		System.out.println("\n\n Something went wrong!");
	}
	
	@Given("a TreeManager")
	public void messengerSetup(){
		manager = new TreeManager();
		tree = new Tree();
	}
	
	@When("set name to: $name, type to: $type and amount to: $amount")
	public void setArguments(String name, String type, int amount){
		tree.setName(name);
		tree.setType(type);
		tree.setAmount(amount);
	}
	
	@When("removing tree where name is $name, type is $type and amount is $amount")
	public void setArgumentsToRemove(String name, String type, int amount){
		tree.setName(name);
		tree.setType(type);
		tree.setAmount(amount);
	}

	@Then("adding should increase trees by $amount")
	public void amountShouldBe(int amount) {
		
		int amountBefore = manager.countTrees();
		manager.addTree(tree);
		int amountAfter = manager.countTrees();
		
		assertEquals(amount, amountAfter - amountBefore);
	}
	
	@Then("added tree should have name $name")
	public void nameShouldBe(String name) {
		
		manager.addTree(tree);
		List<Tree> trees = new ArrayList<Tree>();
		
		trees = manager.getAllList();
		
		String addedName = trees.get(trees.size() - 1).getName();
		
		assertEquals(name, addedName);
	}
	
	@Then("trees should decrease by $amount")
	public void amountRemovedShouldBe(int amount) {
		
		manager.addTree(tree);
		manager.addTree(tree);
		int amountBefore = manager.countTrees();
		manager.removeTree(tree);
		int amountAfter = manager.countTrees();
		
		assertEquals(amount, amountBefore - amountAfter);
	}


}
