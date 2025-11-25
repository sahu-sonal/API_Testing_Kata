Feature: Partial Update Booking API
  As a user
  I want to partially update a booking
  So that I can modify specific fields without updating the entire booking

  Background:
    Given the booking API is available
    And I have a valid authentication token

  @positive @sanity @regression
  Scenario: Partially update booking firstname
    And I have created a booking
    When I partially update the created booking with firstname "NewFirstName"
    Then the response status code should be 200
    And the partial booking details should be returned

  @positive @sanity @regression
  Scenario: Partially update booking lastname
    And I have created a booking
    When I partially update the created booking with lastname "NewLastName"
    Then the response status code should be 200
    And the partial booking details should be returned

  @positive @sanity @regression
  Scenario: Partially update booking depositpaid
    And I have created a booking
    When I partially update the created booking with depositpaid "false"
    Then the response status code should be 200
    And the partial booking details should be returned

  @negative @regression
  Scenario: Partially update booking without authentication
    When I partially update booking ID 1 without authentication
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

  @negative @regression
  Scenario Outline: Partially update booking with invalid token scenarios
    When I partially update booking ID 1 with firstname "NewName" and "<token_type>" token
    Then the response status code should be 401
    And the response should contain error message "Authentication required"

    Examples:
      | test_case      | token_type |
      | Invalid token  | invalid    |
      | Empty token    | empty      |

  @negative @regression
  Scenario: Partially update non-existent booking
    When I partially update booking ID 99999 with firstname "NewName"
    Then the response status code should be 404

  @negative @regression
  Scenario Outline: Partially update booking with invalid firstname
    And I have created a booking
    When I partially update the created booking with firstname "<firstname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                    | firstname | expected_error                |
      | Firstname too short          | Jo        | size must be between 3 and 18 |
      | Firstname too long           | ThisIsAVeryLongFirstNameThatExceedsLimit | size must be between 3 and 18 |
      | Empty firstname              |           | size must be between 3 and 18 |

  @negative @regression
  Scenario Outline: Partially update booking with invalid lastname
    And I have created a booking
    When I partially update the created booking with lastname "<lastname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case                 | lastname | expected_error                |
      | Lastname too short        | Do       | size must be between 3 and 18 |
      | Lastname too long         | ThisIsAVeryLongLastNameThatExceedsLimit | size must be between 3 and 18 |
      | Empty lastname            |          | size must be between 3 and 18 |

  @negative @regression
  Scenario Outline: Partially update booking with boundary values for names
    And I have created a booking
    When I partially update the created booking with firstname "<firstname>" and lastname "<lastname>"
    Then the response status code should be 400
    And the response should contain validation error "<expected_error>"

    Examples:
      | test_case              | firstname | lastname | expected_error                |
      | Firstname below min     | Ab        | ValidName | size must be between 3 and 18 |
      | Lastname below min      | ValidName | Ab       | size must be between 3 and 18 |
      | Firstname above max     | ThisIsAVeryLongFirstNameThatExceedsLimit | ValidName | size must be between 3 and 18 |
      | Lastname above max      | ValidName | ThisIsAVeryLongLastNameThatExceedsLimit | size must be between 3 and 18 |

