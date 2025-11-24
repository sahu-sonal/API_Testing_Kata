Feature: Update Booking API
  As a user
  I want to update an entire booking
  So that I can modify booking details

  Background:
    Given the booking API is available

  @positive @sanity @regression
  Scenario: Update booking with authentication
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking
    Then the response status code should be 200
    And the booking should be updated successfully

  @negative @regression
  Scenario: Update booking without authentication
    When I update booking ID 1 without authentication
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

  @negative @regression
  Scenario Outline: Update booking with invalid token scenarios
    When I update booking ID 1 with "<token_type>" token
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

    Examples:
      | test_case      | token_type |
      | Invalid token  | invalid    |
      | Empty token    | empty      |

  @negative @regression
  Scenario: Update non-existent booking
    Given I have a valid authentication token
    When I update booking ID 99999 with new booking details
    Then the response status code should be 404

  @negative @regression
  Scenario Outline: Update booking with invalid firstname
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with firstname "<firstname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                    | firstname | expected_error                |
      | Firstname too short          | Jo        | size must be between 3 and 18 |
      | Firstname too long           | ThisIsAVeryLongFirstNameThatExceedsLimit | size must be between 3 and 18 |

  @negative @regression
  Scenario Outline: Update booking with invalid lastname
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with lastname "<lastname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                 | lastname | expected_error                |
      | Lastname too short        | Do       | size must be between 3 and 18 |
      | Lastname too long         | ThisIsAVeryLongLastNameThatExceedsLimit | size must be between 3 and 18 |

  @negative @regression
  Scenario Outline: Update booking with invalid email
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with email "<email>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                  | email            | expected_error                        |
      | Email without @ symbol     | invalidemail     | must be a well-formed email address  |
      | Email missing @ symbol     | testexample.com  | must be a well-formed email address  |
      | Email incomplete domain    | test@            | must be a well-formed email address  |
      | Email with spaces          | test @example.com | must be a well-formed email address |
      | Email missing TLD          | test@example     | must be a well-formed email address  |
      | Email with multiple @      | test@@example.com | must be a well-formed email address |

  @negative @regression
  Scenario Outline: Update booking with invalid phone
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with phone "<phone>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case           | phone | expected_error              |
      | Phone too short     | 12345 | size must be between 11 and 21 |
      | Phone too long      | 1234567890123456789012 | size must be between 11 and 21 |

  @negative @regression
  Scenario Outline: Update booking with invalid dates
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with checkin "<checkin>" and checkout "<checkout>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                      | checkin    | checkout   | expected_error            |
      | Checkout date before checkin   | 2025-10-15 | 2025-10-13 | Failed to update booking |
      | Same checkin and checkout date | 2025-10-15 | 2025-10-15 | Failed to update booking |
      | Invalid date format            | 15-10-2025 | 2025-10-15 | Failed to update booking |
      | Invalid date format            | 2025/10/15 | 2025-10-15 | Failed to update booking |

  @negative @regression
  Scenario Outline: Update booking with missing required fields
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with missing "<field>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case      | field         | expected_error                |
      | Missing firstname | firstname | must not be null             |
      | Missing lastname  | lastname   | must not be null             |
      | Missing email     | email      | must not be null             |
      | Missing phone     | phone      | must not be null             |
      | Missing roomid    | roomid     | must not be null             |
      | Missing depositpaid | depositpaid | must not be null          |
      | Missing bookingdates | bookingdates | must not be null         |

  @negative @regression
  Scenario Outline: Update booking with empty required fields
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with empty "<field>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case        | field      | expected_error                |
      | Empty firstname  | firstname  | size must be between 3 and 18 |
      | Empty lastname   | lastname   | size must be between 3 and 18 |
      | Empty email      | email      | must be a well-formed email address |
      | Empty phone      | phone      | size must be between 11 and 21 |

  @negative @regression
  Scenario Outline: Update booking with invalid room ID
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with roomid "<roomid>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case        | roomid | expected_error            |
      | Negative room ID | -1     | Failed to update booking |
      | Zero room ID     | 0      | Failed to update booking |

  @negative @regression
  Scenario Outline: Update booking with boundary values for names
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with firstname "<firstname>" and lastname "<lastname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case              | firstname | lastname | expected_error                |
      | Firstname below min     | Ab        | Doe      | size must be between 3 and 18 |
      | Lastname below min      | John      | Ab       | size must be between 3 and 18 |
      | Firstname above max     | ThisIsAVeryLongFirstNameThatExceedsLimit | Doe | size must be between 3 and 18 |
      | Lastname above max      | John      | ThisIsAVeryLongLastNameThatExceedsLimit | size must be between 3 and 18 |

  @negative @regression
  Scenario: Update booking with null depositpaid
    Given I have a valid authentication token
    And I have created a booking
    When I update the created booking with null depositpaid
    Then the response status code should be 400
    And the response should contain validation error "must not be null"

