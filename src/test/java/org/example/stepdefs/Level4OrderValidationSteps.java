package org.example.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.example.matchers.OrderDateMatcher;
import org.example.matchers.RangeMatcher;
import org.example.matchers.RangeMatcherV2;
import org.opentest4j.AssertionFailedError;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Level4OrderValidationSteps {

    private String actualJson;

    @Given("I have the following order JSON:")
    public void i_have_the_following_order_json(String docString) {
        System.out.println("[LOG] Order JSON: " + docString);
        this.actualJson = docString;
    }

    @When("the API returns JSON as:")
    public void theApiReturnsJson(String docString) {
        System.out.println("[LOG] Received JSON: " + docString);
        this.actualJson = docString;
    }

    @Then("the order JSON should conform to the golden contract file {string}")
    public void the_order_json_should_conform_to_the_golden_contract_file(String goldenFilename) throws Exception {
        System.out.printf("[LOG] Trying to load Golden JSON filename: %s\n", goldenFilename);

        // Here you would typically load the golden contract file and validate the order JSON against it.
        String golden = loadGolden("/golden/" + goldenFilename);
        System.out.println("[LOG] Comparing Actual JSON using Custom Matchers...");

        // Json-unit or a similar library would be used to perform the validation.
        JsonAssertions.assertThatJson(actualJson)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .withMatcher("orderDateAsYYYYMMDD", OrderDateMatcher.orderDate())
                .isEqualTo(golden);
    }

    private String loadGolden(String resourcePath) throws Exception {
        URL url = getClass().getResource(resourcePath);
        if (url == null) throw new IllegalArgumentException("Golden file not found: " + resourcePath);
        return Files.readString(Paths.get(url.toURI()));
    }

    @Then("processingMs should match range in the golden file {string}")
    public void processingmsShouldMatchRangeInTheGoldenFile(String goldenFilename) throws Exception {
        System.out.printf("[LOG] Trying to load Golden JSON filename: %s\n", goldenFilename);
        String golden = loadGolden("/golden/" + goldenFilename);
        System.out.println("golden = " + golden);
        System.out.println("[LOG] Comparing Actual JSON using Custom Matchers...");

        // Assuming the golden file contains a range for processingMs, you would implement the logic here
        // For example, if the golden file has a specific range for processingMs, you would validate it against actualJson
        JsonAssertions.assertThatJson(actualJson)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .withMatcher("rangeMatcher", RangeMatcher.range())
                .isEqualTo(golden);
    }

    // TO DO: Implement the negative scenario for processingMs
/*    @Then("processingMs should NOT match range in the golden file {string}")
    public void processing_ms_should_not_match_range_in_the_golden_file(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }*/


/*    @Then("the order JSON should NOT conform to the golden contract file {string}")
    public void theOrderJSONShouldNOTConformToTheGoldenContractFile(String goldenFileName) throws Exception {
        System.out.printf("[LOG] Trying to load Golden JSON filename: %s\n", goldenFileName);
        try {
            String golden = loadGolden("/golden/" + goldenFileName);
            System.out.println("[LOG] Comparing Actual JSON using Custom Matchers...");

            // This will throw an AssertionError if the JSON matches, which is what we want
            JsonAssertions.assertThatJson(actualJson)
                    .when(Option.IGNORING_EXTRA_FIELDS)
                    .withMatcher("orderDateAsYYYYMMDD", OrderDateMatcher.orderDate())
                    .isNotEqualTo(golden);

            System.out.println("[LOG] Order JSON did NOT match the golden contract as expected.");
        } catch (AssertionError e) {
            System.err.println("[ERROR] Order JSON matched the golden contract when it should not have!");
            throw e; // Re-throw to fail the test
        }
    }*/

    // Negative scenario
    @Then("the order JSON should NOT conform to the golden contract file {string}")
    public void theOrderJSONShouldNOTConformToTheGoldenContractFile(String goldenFileName) throws Exception {
        System.out.printf("[LOG] Trying to load Golden JSON filename: %s\n", goldenFileName);
        String golden = loadGolden("/golden/" + goldenFileName);
        assertThatThrownBy(() -> {
            JsonAssertions.assertThatJson(actualJson)
                    .when(Option.IGNORING_EXTRA_FIELDS)
                    .withMatcher("orderDateAsYYYYMMDD", OrderDateMatcher.orderDate())
                    .isEqualTo(golden);
        }).isInstanceOf(AssertionFailedError.class);
        System.out.println("[LOG] Finished Comparing Actual JSON using Custom Matchers...");
    }

    @Then("JSON assertion with range fails with message {string} when matched against golden file {string}")
    public void jsonAssertionWithRangeFailsWithMessageWhenMatchedAgainstGoldenFile(String expectedMessage, String goldenFile) throws Exception {
        System.out.println("[LOG] Trying to load Golden JSON filename: " + goldenFile);
        //String golden = loadGolden(goldenFile);
        String golden = loadGolden("/golden/" + goldenFile);

        AssertionError error = null;
        try {
            JsonAssertions.assertThatJson(actualJson)
                    .withOptions(Option.IGNORING_EXTRA_FIELDS)
                    .withMatcher("rangeMatcher", new RangeMatcherV2())
                    .isEqualTo(golden);
        } catch (AssertionError ae) {
            error = ae;
            System.out.printf("[LOG][StepDef] Assertion failed as expected: %s%n", ae.getMessage());
        }

        assertNotNull(error, "[LOG][StepDef] Expected assertion failure to occur!");
        assertTrue(error.getMessage().contains(expectedMessage), "[LOG][StepDef] Error message should mention: " + expectedMessage);
    }
}
