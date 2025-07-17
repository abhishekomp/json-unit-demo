Feature: Devices array with allowed types

  Scenario: POSITIVE - Each device's type is allowed
    When the device API returns JSON:
      """
      { "devices": [
          { "id": "d1", "type": "A" },
          { "id": "d2", "type": "C" }
        ] }
      """
    Then it should match the device golden "device_typearray_golden.json"

  Scenario: NEGATIVE - Some device type is not allowed
    When the device API returns JSON:
      """
      { "devices": [
          { "id": "d1", "type": "A" },
          { "id": "d2", "type": "X" }
        ] }
      """
    Then the match should fail with message "the 'type' value does not match any value in [A, B, C] for item: {\"id\":\"d2\",\"type\":\"X\"}" when matched against the device golden "device_typearray_golden.json"

#  Scenario: NEGATIVE - Device type is missing
#    When the device API returns JSON:
#    """
#    { "devices": [
#        { "id": "d1" },
#        { "id": "d2", "type": "C" }


#    Scenario: NEGATIVE - Empty devices array
#        When the device API returns JSON:
#        """
#        { "devices": [] }
#        """
#        Then the match should fail with message "the 'devices' array is empty" when matched against the device golden "device_typearray_golden.json"