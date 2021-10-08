package seng202;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
	//---------------------------------------------------------------------------------------------------
	//Steps for the "import" feature
	//Preconditions
	@Given("No records are in the application")
	public void noRecordsAreInTheApplication() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Given("There are records in the application")
	public void thereAreRecordsInTheApplication() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Given("There are {int} active filters")
	public void thereAreActiveFilters(Integer activeFilters) {
		// Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	//Actions
	@When("A file is imported with {int} valid lines")
	public void aFileIsImportedWithValidLines(Integer validLines) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	//Postconditions
	@Then("The application should have {int} records stored")
	public void theApplicationShouldHaveRecordsStored(Integer records) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("The application should have {int} records active")
	public void theApplicationShouldHaveRecordsActive(Integer records) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	//---------------------------------------------------------------------------------------------------
}
