package org.example.stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.javacrumbs.jsonunit.core.Option;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class Level2FlexibleJsonSteps {
    private String actualJson;

    @When("I get the stubbed API response")
    public void i_get_the_stubbed_api_response(String docString) {
        this.actualJson = docString;
        System.out.println("[LOG] API response: " + actualJson);
    }

    @Then("the response should loosely match the golden file {string}")
    public void loose_match_golden(String goldenFile) throws Exception {
        String expectedJson = readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Golden: " + expectedJson);
        assertThatJson(actualJson)
                .when(Option.IGNORING_EXTRA_FIELDS) // Ignore extra fields
                .isEqualTo(expectedJson);
        System.out.println("[LOG] Loose match succeeded!");
    }

    @When("I get the array API response")
    public void i_get_the_array_api_response(String docString) {
        this.actualJson = docString;
        System.out.println("[LOG] Array API response: " + actualJson);
    }

    @Then("the genres array should match order-insensitively")
    public void array_order_match(String expectedJson) {
        System.out.println("[LOG] Comparing arrays order-insensitively...");
        assertThatJson(actualJson)
                .when(Option.IGNORING_ARRAY_ORDER)  // Ignore order of array elements
                .isEqualTo(expectedJson);
        System.out.println("[LOG] Array order-insensitive match succeeded!");
    }

    @When("I get the user account response")
    public void i_get_the_user_account_response(String docString) {
        this.actualJson = docString;
        System.out.println("[LOG] User account JSON: " + actualJson);
    }

    @Then("the response should match the golden file {string} ignoring path {string}")
    public void match_ignoring_path(String goldenFile, String ignoredPath) throws Exception {
        String expectedJson = readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Golden: " + expectedJson);
        assertThatJson(actualJson)
                .whenIgnoringPaths(ignoredPath) // Ignore specific path
                .isEqualTo(expectedJson);
        System.out.println("[LOG] Ignore-path match succeeded!");
    }

    // Re-use helper from Level 1
    private String readGoldenJson(String resourcePath) throws Exception {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) throw new IllegalArgumentException("Golden JSON not found: " + resourcePath);
        return java.nio.file.Files.readString(java.nio.file.Paths.get(url.toURI()), java.nio.charset.StandardCharsets.UTF_8);
    }

    @When("I get the response")
    public void i_get_the_response(String docString) {
        System.out.println("[LOG] API response: " + actualJson);
        this.actualJson = docString;
    }

    @Then("the response should match the golden file {string} ignoring paths {string} and ignoring array order")
    public void theResponseShouldMatchTheGoldenFileIgnoringPathsAndIgnoringArrayOrder(String goldenFile, String ignoredPaths) throws Exception {
        String expectedJson = readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Golden: " + expectedJson);    // Log the expected JSON (golden file content)
        String[] paths = ignoredPaths.split("\\s+");
        assertThatJson(actualJson)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .when(Option.IGNORING_ARRAY_ORDER)
                .whenIgnoringPaths(paths)
                .isEqualTo(expectedJson);
        System.out.println("[LOG] Flexible match succeeded for file: " + goldenFile);
        // To use scenario.log in your Cucumber Java project, inject the Scenario object into your step definition methods. This allows you to log messages that will appear in the Cucumber report.
        // scenario.log("[LOG] Flexible match succeeded for file: " + goldenFile);
    }

    @Then("the JSON should match the golden file {string} with options {string} and ignoring paths {string}")
    public void theJSONShouldMatchTheGoldenFileWithOptionsAndIgnoringPaths(String goldenFile, String options, String ignoredPaths) throws Exception {
        String expectedJson = readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Golden: " + expectedJson);    // Log the expected JSON (golden file content)
        String[] paths = ignoredPaths.split("\\s+");

        assertThatJson(actualJson)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .when(Option.IGNORING_ARRAY_ORDER)
                .whenIgnoringPaths(paths)
                .isEqualTo(expectedJson);

        System.out.println("[LOG] JSON match succeeded for file: " + goldenFile);
        // scenario.log("[LOG] JSON match succeeded for file: " + goldenFile);
    }

/*    @Then("the JSON response should match the golden file {string} with options {string} and ignoring paths {string}")
    public void theJSONRespShouldMatchTheGoldenFileWithOptionsAndIgnoringPaths(String goldenFile, String options, String ignoredPaths) throws Exception {
        String expectedJson = readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Golden: " + expectedJson);    // Log the expected JSON (golden file content)

        String[] optionArr = options.split(",");
        String[] paths = ignoredPaths.split("\\s+");
        // Build Option...
        *//*
        ConfigurableJsonAssert is not recognized because it is not a public class in the json-unit-assertj library. The correct usage is to work with the object returned by assertThatJson(actualJson), which is already a ConfigurableJsonAssert internally, but the class itself is package-private and cannot be imported or referenced directly.
        ConfigurableJsonAssert is the concrete implementation class for the AssertJ-style assertion in json-unit.
        But in normal, idiomatic use, you don’t ever need to use it directly or import it in your own code.
        import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
         *//*
        //net.javacrumbs.jsonunit.assertj.ConfigurableJsonAssert cmp = assertThatJson(actualJson);
        var cmp = assertThatJson(actualJson);
        for (String opt : optionArr) {
            cmp = cmp.when(Option.valueOf(opt.trim()));
        }
        cmp = cmp.whenIgnoringPaths(paths);
        cmp.isEqualTo(expectedJson);
    }*/
}
