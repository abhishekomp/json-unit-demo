package org.example.myHooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class ScenarioHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("[HOOK] Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("[HOOK] Finished scenario: " + scenario.getName());
    }
}