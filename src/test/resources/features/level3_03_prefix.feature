Feature: Parameterized prefix matcher for userId

  Scenario: userId should have a USR- prefix
    When the API returns JSON:
      """
      {
        "userId": "USR-001234",
        "role": "admin"
      }
      """
    Then it should match prefix golden "prefix_golden.json" with prefix "USR-"