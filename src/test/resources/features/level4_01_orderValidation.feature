Feature: Order JSON Validation

  Scenario: Order JSON should match contract rules
    Given I have the following order JSON:
      """
      {
        "orderId": "ORD-56789",
        "orderDate": "2025-07-06",
        "orderPlacedAt": "2025-07-06T15:30:00Z",
        "expectedDeliveryDate": "2025-07-10",
        "isExpressDelivery": false,
        "itemCount": 2
      }
      """
    Then the order JSON should conform to the golden contract file "golden_order.json"

#  Scenario: Order JSON should not match contract rules
#    Given I have the following order JSON:
#      """
#      {
#        "orderId": "ORD-56789",
#        "orderDate": "2025-07-06T15:30:00Z",
#        "orderPlacedAt": "2025-07-06T15:30:00Z",
#        "expectedDeliveryDate": "2025-07-10",
#        "isExpressDelivery": false,
#        "itemCount": 2
#      }
#      """
#    Then the order JSON should conform to the golden contract file "golden_order.json"

  Scenario: Order JSON should NOT match contract rules
    Given I have the following order JSON:
      """
      {
        "orderId": "ORD-56789",
        "orderDate": "2025-07-06T15:30:00Z",
        "orderPlacedAt": "2025-07-06T15:30:00Z",
        "expectedDeliveryDate": "2025-07-10",
        "isExpressDelivery": false,
        "itemCount": 2
      }
      """
    Then the order JSON should NOT conform to the golden contract file "golden_order.json"
