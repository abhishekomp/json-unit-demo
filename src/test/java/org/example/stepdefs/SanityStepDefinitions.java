package org.example.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SanityStepDefinitions {

    @Given("the test environment is up")
    public void theTestEnvironmentIsUp() {
        //System.out.printf("[LOG] Expected count: %d, Actual count: %d%n", expectedCount, bookCount);
        System.out.println("[LOG] Initializing test environment for Cucumber testing...");
    }

    @Then("everything should work")
    public void everythingShouldWork() {

        System.out.println("[LOG] Cucumber Sanity check passed!");
    }
}