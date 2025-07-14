package org.example.stepdefs;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import java.nio.file.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonSteps {

    private String actualJson;

    @When("I get the stubbed API response for {string}")
    public void i_get_the_stubbed_api_response_for(String title) {
        // Stub the response as a string (in a real test, you'd call the API)
        if ("Dune".equals(title)) {
            actualJson = "{ \"title\": \"Dune\", \"author\": \"Frank Herbert\", \"year\": 1965 }";
        } else {
            actualJson = "{ \"title\": \"" + title + "\", \"author\": \"Unknown\", \"year\": 2020 }";
        }
        System.out.println("[LOG] Actual JSON: " + actualJson);
    }

    @Then("the response should match the golden file {string}")
    public void the_response_should_match_golden(String goldenFile) throws Exception {
        String expectedJson = readGoldenJson("/golden/" + goldenFile);
        System.out.println("[LOG] Golden JSON: " + expectedJson);

        // Simple, strict JSON compare
        assertThatJson(actualJson).isEqualTo(expectedJson);

        System.out.println("[LOG] JSONs matched (LEVEL 1)!");
    }

    /**
     * Reads the golden JSON file from the resources' directory.
     *
     * @param resourcePath the path to the golden JSON file
     * @return the content of the JSON file as a string
     * @throws Exception if the file cannot be read
     */
    private String readGoldenJson(String resourcePath) throws Exception {
        URL url = getClass().getResource(resourcePath);
        if (url == null) throw new IllegalArgumentException("Golden JSON not found: " + resourcePath);
        return Files.readString(Paths.get(url.toURI()), StandardCharsets.UTF_8);
    }
}