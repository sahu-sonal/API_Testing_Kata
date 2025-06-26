

Feature: Partial update a booking
  Background:
    Given I have a valid token

  Scenario: Update a booking
    Given I want to update a booking for a holiday
    When I make a request to update first name and Last name
    Then I should get the success status code


