Feature: Delete Booking API
  As a user
  I want to delete a booking
  So that I can cancel a reservation

  Background:
    Given the booking API is available
    And I have a valid authentication token

  @positive @sanity @regression
  Scenario: Delete booking with authentication
    And I have created a booking
    When I delete the created booking
    Then the response status code should be 200

  @negative @regression
  Scenario: Delete booking without authentication
    When I delete booking ID 1 without authentication
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

  @negative @regression
  Scenario Outline: Delete booking with invalid token scenarios
    When I delete booking ID 1 with "<token_type>" token
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

    Examples:
      | test_case      | token_type |
      | Invalid token  | invalid    |
      | Empty token    | empty      |

  @negative @regression
  Scenario: Delete non-existent booking
    When I delete booking ID 99999
    Then the response status code should be 404

  @negative @regression
  Scenario Outline: Delete booking with invalid ID
    When I delete booking ID <booking_id>
    Then the response status code should be 404

    Examples:
      | test_case   | booking_id |
      | Negative ID | -1         |
      | Zero ID     | 0          |

