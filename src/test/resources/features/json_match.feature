Feature: API JSON matches golden file

  Scenario: Match the stubbed API response with the golden template
    When I get the stubbed API response for "Dune"
    Then the response should match the golden file "expected_minimal.json"