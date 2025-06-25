

Feature: Get list of bookings of a particular room
  Background:
    Given I have a valid token

  Scenario: Successful list of a room
    Given I want to see the list of rooms
    When I want to read the bookings for a room
    Then I should receive all existing bookings



