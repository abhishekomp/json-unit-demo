Feature: Devices array with per-object rule

  Scenario: Each device type must be A, B, or C
    When the device API returns JSON:
      """
      {
        "devices": [
          { "id": "d1", "type": "A" },
          { "id": "d2", "type": "C" }
        ]
      }
      """
    Then it should match the device golden "device_typearray_golden.json"

#  Scenario: Verify the test fails if device type is not one of A, B, C
#    When the device API returns JSON:
#      """
#      {
#        "devices": [
#          { "id": "d1", "type": "A" },
#          { "id": "d2", "type": "X" }
#        ]
#      }
#      """
#    Then it should match the device golden "device_typearray_golden.json"