Feature: Create Booking API
  As a user
  I want to create a new booking
  So that I can reserve a room

  Background:
    Given the booking API is available

  @positive @sanity @regression
  Scenario: Create booking with valid data
    When I create a booking with valid booking details
    Then the response status code should be 201
    And the response should contain a booking id
    And the booking details should match the request

  @negative @regression
  Scenario Outline: Create booking with invalid firstname
    When I create a booking with firstname "<firstname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                    | firstname | expected_error                |
      | Firstname too short          | Jo        | size must be between 3 and 18 |
      | Firstname too long           | ThisIsAVeryLongFirstNameThatExceedsLimit | size must be between 3 and 18 |

  @negative @regression
  Scenario Outline: Create booking with invalid lastname
    When I create a booking with lastname "<lastname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                 | lastname | expected_error                |
      | Lastname too short        | Do       | size must be between 3 and 18 |
      | Lastname too long         | ThisIsAVeryLongLastNameThatExceedsLimit | size must be between 3 and 18 |

  @negative @regression
  Scenario Outline: Create booking with invalid email
    When I create a booking with email "<email>"
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
  Scenario Outline: Create booking with invalid phone
    When I create a booking with phone "<phone>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case           | phone | expected_error              |
      | Phone too short     | 12345 | size must be between 11 and 21 |
      | Phone too long      | 1234567890123456789012 | size must be between 11 and 21 |

  @negative @regression
  Scenario Outline: Create booking with invalid dates
    When I create a booking with checkin "<checkin>" and checkout "<checkout>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                      | checkin    | checkout   | expected_error            |
      | Checkout date before checkin   | 2025-10-15 | 2025-10-13 | Failed to create booking |
      | Same checkin and checkout date | 2025-10-15 | 2025-10-15 | Failed to create booking |
      | Invalid date format            | 15-10-2025 | 2025-10-15 | Failed to create booking |
      | Invalid date format            | 2025/10/15 | 2025-10-15 | Failed to create booking |

  @negative @regression
  Scenario Outline: Create booking with missing required fields
    When I create a booking with missing "<field>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case      | field         | expected_error                |
      | Missing firstname | firstname | Firstname should not be blank             |
      | Missing lastname  | lastname   | Lastname should not be blank             |
      | Missing email     | email      | must not be null             |
      | Missing phone     | phone      | must not be null             |
      | Missing roomid    | roomid     | must be greater than or equal to 1             |
      | Missing depositpaid | depositpaid | must not be null          |
      | Missing bookingdates | bookingdates | must not be null         |

  @negative @regression
  Scenario Outline: Create booking with empty required fields
    When I create a booking with empty "<field>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case        | field      | expected_error                |
      | Empty firstname  | firstname  | size must be between 3 and 18 |
      | Empty lastname   | lastname   | size must be between 3 and 18 |
      | Empty email      | email      | mmust not be empty |
      | Empty phone      | phone      | size must be between 11 and 21 |

  @negative @regression
  Scenario Outline: Create booking with invalid room ID
    When I create a booking with roomid "<roomid>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case        | roomid | expected_error            |
      | Negative room ID | -1     | must be greater than or equal to 1 |
      | Zero room ID     | 0      | must be greater than or equal to 1 |

  @negative @regression
  Scenario Outline: Create booking with boundary values for names
    When I create a booking with firstname "<firstname>" and lastname "<lastname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case              | firstname | lastname | expected_error                |
      | Firstname below min     | Ab        | Doe      | size must be between 3 and 18 |
      | Lastname below min      | John      | Ab       | size must be between 3 and 18 |
      | Firstname above max     | ThisIsAVeryLongFirstNameThatExceedsLimit | Doe | size must be between 3 and 18 |
      | Lastname above max      | John      | ThisIsAVeryLongLastNameThatExceedsLimit | size must be between 3 and 18 |

  @negative @regression
  Scenario: Create booking with null depositpaid
    When I create a booking with null depositpaid
    Then the response status code should be 400
    And the response should contain validation error "must not be null"

  @negative @regression
  Scenario: Create duplicate booking with same details
    Given I have created a booking with valid details
    When I try to create the same booking again
    Then the response status code should be 409
    And the response should contain validation error "Failed to create booking"

