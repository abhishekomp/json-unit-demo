package org.example.stepdefs;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.example.matchers.AfterTimestampMatcher;
import org.example.matchers.PrefixMatcher;
import org.example.matchers.UuidHamcrestMatcher;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.net.URL;
import java.time.Instant;

public class UuidMatcherSteps {
    private String actualJson;

    @When("the API returns JSON:")
    public void api_returns_json(String docString) {
        this.actualJson = docString;
    }

    @Then("it should match the golden {string}")
    public void should_match_golden(String goldenFilename) throws Exception {
        String golden = loadGolden("/golden/" + goldenFilename);
        System.out.println("[LOG] Comparing JSON with UUID matcher...");
        //UuidHamcrestMatcher uuidMatcher = new UuidHamcrestMatcher();
        JsonAssertions.assertThatJson(actualJson)
                .withMatcher("uuidMatcher", UuidHamcrestMatcher.uuid())
                .isEqualTo(golden);
    }

    @Then("it should match timestamp golden {string}")
    public void should_match_timestamp_golden(String goldenFilename) throws Exception {
        String golden = loadGolden("/golden/" + goldenFilename);
        // Use the current test start time as the reference
        Instant reference = Instant.parse("2023-07-15T00:00:00Z"); // Example reference
        System.out.println("[LOG] Using reference: " + reference);
        JsonAssertions.assertThatJson(actualJson)
                .withMatcher("afterMatcher", AfterTimestampMatcher.after(reference))
                .isEqualTo(golden);
    }

    @Then("it should match prefix golden {string} with prefix {string}")
    public void should_match_prefix_golden(String goldenFilename, String prefix) throws Exception {
        String golden = loadGolden("/golden/" + goldenFilename);
        JsonAssertions.assertThatJson(actualJson)
                .withMatcher("prefixMatcher", PrefixMatcher.prefix(prefix)) // Pass param here
                .isEqualTo(golden);
    }

    private String loadGolden(String resourcePath) throws Exception {
        URL url = getClass().getResource(resourcePath);
        if (url == null) throw new IllegalArgumentException("Golden file not found: " + resourcePath);
        return Files.readString(Paths.get(url.toURI()));
    }
}