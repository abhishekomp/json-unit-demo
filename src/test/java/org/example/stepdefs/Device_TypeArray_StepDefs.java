package org.example.stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.example.matchers.TypeInSetArrayMatcher;
import org.example.utils.GoldenFileReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Device_TypeArray_StepDefs {

    private String actualJson;

    /**
     * This step definition simulates receiving a JSON response from a device API.
     * It logs the received JSON for debugging purposes.
     *
     * @param docString the JSON response as a string
     */
    @When("the device API returns JSON:")
    public void the_device_api_returns_json(String docString) {
        System.out.println("[LOG] docString: " + docString);
        // In a real scenario, you would parse the JSON and store it for further validation
        // For demonstration; we just log the received JSON
        actualJson = docString; // Store the JSON for later comparison
    }

    @Then("it should match the device golden {string}")
    public void it_should_match_the_device_golden(String goldenFile) throws Exception {
        System.out.println("[LOG] it_should_match_the_device_golden: " + goldenFile);

        // Read the golden JSON file from resources
        String expectedJson = GoldenFileReader.readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Expected JSON: " + expectedJson);
        // Compare the actual JSON with the expected JSON
        // In a real test, you would have the actual JSON to compare against
        // For demonstration, let's assume the actual JSON is stored in a variable
        JsonAssertions.assertThatJson(actualJson)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .withMatcher("typeInSetMatcher", new TypeInSetArrayMatcher())
                .isEqualTo(expectedJson);
    }

    @Then("the match should fail with message {string} when matched against the device golden {string}")
    public void theMatchShouldFailWithMessageWhenMatchedAgainstTheDeviceGolden(String expectedErrorMessage, String goldenFile) throws Exception {
        //System.out.println("[LOG] The expected error message is: '" + expectedErrorMessage + "'");
        System.out.printf("[LOG] The expected error message is: '%s'\n", expectedErrorMessage);
        System.out.printf("[LOG] The golden file is: '%s'\n", goldenFile);
        String golden = GoldenFileReader.readGoldenJson("/golden/" + goldenFile);
        AssertionError error = null;
        try {
            System.out.printf("[LOG][StepDef] Asserting devices array fails.%n");
            JsonAssertions.assertThatJson(actualJson)
                    .withMatcher("typeInSetMatcher", new TypeInSetArrayMatcher())
                    .isEqualTo(golden);
        } catch (AssertionError ae) {
            System.out.printf("[LOG][StepDef] Assertion failed as expected: %s%n", ae.getMessage());
            error = ae;
        }
        assertTrue(error != null, "[LOG][StepDef] Expected assertion to fail!");
        System.out.printf("[LOG][StepDef] Assertion failed as expected: %s%n", error.getMessage());
        // Check if the error message contains the expected text
        System.out.println("Expected error message contained in the assertion error? " + error.getMessage().contains(expectedErrorMessage));
        assertTrue(error.getMessage().contains(expectedErrorMessage), "[LOG][StepDef] Error message should include matcher description.");
    }
}
