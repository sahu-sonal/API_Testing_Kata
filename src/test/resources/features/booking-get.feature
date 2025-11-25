Feature: Get Booking API
  As a user
  I want to retrieve booking details by ID
  So that I can view booking information

  Background:
    Given the booking API is available
    And I have a valid authentication token

  @positive @sanity @regression
  Scenario: Get booking by ID with authentication
    And I have created a booking
    When I get the created booking
    Then the response status code should be 200
    And the booking details should be returned

  @negative @regression
  Scenario: Get booking by ID without authentication
    When I get booking by ID 1 without authentication
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

  @negative @regression
  Scenario Outline: Get booking with invalid token scenarios
    When I get booking by ID 1 with "<token_type>" token
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

    Examples:
      | test_case      | token_type |
      | Invalid token  | invalid    |
      | Empty token    | empty      |

  @negative @regression
  Scenario: Get non-existent booking
    When I get booking by ID 99999
    Then the response status code should be 404

  @negative @regression
  Scenario Outline: Get booking with invalid ID
    When I get booking by ID <booking_id>
    Then the response status code should be 404

    Examples:
      | test_case   | booking_id |
      | Negative ID | -1         |
      | Zero ID     | 0          |

