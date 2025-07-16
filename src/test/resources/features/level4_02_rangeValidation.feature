Feature: Order JSON Validation

  # Positive scenario
  Scenario: Validate the value of processingMs within a range in the JSON response

    When the API returns JSON as:
      """
      {
        "createdAt": "2024-07-15T10:00:00Z",
        "title": "Dune",
        "processingMs": 200
      }
      """
    Then processingMs should match range in the golden file "golden_range.json"

    # Negative scenario
  Scenario: Validate the value of processingMs is NOT within a range in the JSON response (ScenarioV2)

    When the API returns JSON as:
      """
      {
        "createdAt": "2024-07-15T10:00:00Z",
        "title": "Dune",
        "processingMs": 1000
      }
      """
    Then JSON assertion with range fails with message "a number between 100.0 and 400.0" when matched against golden file "golden_range.json"

    # Negative scenario
#  Scenario: Validate the value of processingMs is NOT within a range in the JSON response
#
#    When the API returns JSON as:
#      """
#      {
#        "createdAt": "2024-07-15T10:00:00Z",
#        "title": "Dune",
#        "processingMs": 1000
#      }
#      """
#    Then processingMs should NOT match range in the golden file "golden_range.json"

    #  Scenario: Validate the value of processingMs within a range in the JSON response
#
#    When the API returns JSON as:
#      """
#      {
#        "processingMs": 200
#      }
#      """
#    Then processingMs should match range in the golden file "golden_range.json"