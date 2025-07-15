Feature: Hamcrest matcher for UUID in JSON

  Scenario: API returns a UUID field
    When the API returns JSON:
      """
      {
        "id": "faac4c82-80fc-42df-bc3a-9447a8825cba",
        "title": "Dune"
      }
      """
    Then it should match the golden "uuid_golden.json"