

Feature: Update a booking


  Scenario: To partially update a booking
    Given user has access to endpoint
    When user makes a request to update first name and Last name
    Then user should get the success status code
    And user validates the JSON schema

