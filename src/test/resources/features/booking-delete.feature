Feature: Delete Booking API
  As a user
  I want to delete a booking
  So that I can cancel a reservation

  Background:
    Given the booking API is available

  @positive @sanity @regression
  Scenario: Delete booking with authentication
    Given I have a valid authentication token
    And I have created a booking
    When I delete the created booking
    Then the response status code should be 201

  @negative @regression
  Scenario: Delete booking without authentication
    Given I have created a booking
    When I delete booking ID 1 without authentication
    Then the response status code should be 401
    And the response should contain error message "Unauthorized"

  @negative @regression
  Scenario Outline: Delete booking with invalid token scenarios
    Given I have created a booking
    When I delete booking ID 1 with "<token_type>" token
    Then the response status code should be 401
    And the response should contain error message "Unauthorized"

    Examples:
      | test_case      | token_type |
      | Invalid token  | invalid    |
      | Empty token    | empty      |

  @negative @regression
  Scenario: Delete non-existent booking
    Given I have a valid authentication token
    When I delete booking ID 99999
    Then the response status code should be 404

