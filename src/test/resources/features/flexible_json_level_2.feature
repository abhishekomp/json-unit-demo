Feature: Flexible JSON matching

  Scenario: Extra fields in actual JSON are ignored
    When I get the stubbed API response
      """
      {
        "title": "Dune",
        "author": "Frank Herbert",
        "year": 1965,
        "publisher": "Chilton Books"
      }
      """
    # publisher is an extra field you might receive in http response which is not in the golden file and you want to ignore this property from comparison
    Then the response should loosely match the golden file "expected_minimal.json"

  Scenario: Array order doesn't matter
    When I get the array API response
      """
      {
        "title": "Dune",
        "genres": ["SCIENCE_FICTION", "ADVENTURE"]
      }
      """
    Then the genres array should match order-insensitively
      """
      {
        "title": "Dune",
        "genres": ["ADVENTURE", "SCIENCE_FICTION"]
      }
      """

  Scenario: Ignore a specific field
    When I get the user account response
      """
      {
        "username": "alice",
        "role": "admin",
        "sessionId": "dynamic-session-123"
      }
      """
    Then the response should match the golden file "expected_user.json" ignoring path "$.sessionId"

  Scenario: Flexible match ignoring paths and order
    When I get the response
    """
    {
      "id": "SOME_UUID",
      "createdAt": "2024-06-01T08:00:15Z",
      "user": {
        "username": "bob",
        "active": true
      },
      "roles": ["ADMIN","USER"]
    }
    """
    Then the response should match the golden file "expected_user_2.json" ignoring paths "$.id $.createdAt" and ignoring array order

  Scenario: Flexible match ignoring paths and order
    When I get the response
    """
    {
      "id": "SOME_UUID",
      "createdAt": "2024-06-01T08:00:15Z",
      "user": {
        "username": "bob",
        "active": true
      },
      "roles": ["ADMIN","USER"]
    }
    """
    Then the JSON should match the golden file "expected_user_2.json" with options "IGNORING_EXTRA_FIELDS,IGNORING_ARRAY_ORDER" and ignoring paths "$.id $.createdAt"
