

Feature: Create a booking
  Background:
    Given I have a valid token

  Scenario: Update a booking
    Given I want to book a room for a holiday
    When I make a reservation inserting all the information
    Then I receive the reservation and the process ends successfully


