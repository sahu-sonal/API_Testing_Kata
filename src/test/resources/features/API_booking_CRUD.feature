

Feature: Test the booking functionality
  Background:
    Given I have a valid token

  Scenario: Successful list of a room
    When I want to read the bookings for a room
    Then I should receive all existing bookings

  Scenario: Create a booking
    Given I want to book a room for a holiday
    When I make a reservation inserting all the information
    Then I receive the reservation and the process ends successfully

  Scenario: Update a booking
    Given I want to update a booking for a holiday
    When I make a request to update first name and Last name
    Then I should get the success status code

  Scenario: Delete a booking
    Given I want to delete my booking reservation
    When I make a request to delete it
    Then The reservation should be successfully deleted
