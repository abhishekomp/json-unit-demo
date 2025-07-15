Feature: Field matching after a timestamp

  Scenario: Match createdAt with a timestamp after a reference
    When the API returns JSON:
      """
      {
        "createdAt": "2024-07-15T10:00:00Z",
        "title": "Dune"
      }
      """
    Then it should match timestamp golden "timestamp_golden.json"