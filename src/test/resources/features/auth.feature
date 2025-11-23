Feature: Authentication API
  As a user
  I want to authenticate myself
  So that I can access protected booking endpoints

  Background:
    Given the authentication API is available

  @positive
  Scenario: Successful login with valid credentials
    When I send a POST request to "/auth/login" with valid credentials
    Then the response status code should be 200
    And the response should contain a token

  @negative
  Scenario Outline: Login with invalid credentials
    When I send a POST request to "/auth/login" with username "<username>" and password "<password>"
    Then the response status code should be 401
    And the response should contain an error message "Invalid credentials"

    Examples:
      | test_case        | username      | password      |
      | Invalid username | invaliduser   | password      |
      | Invalid password  | admin         | wrongpassword |

  @negative
  Scenario Outline: Login with empty credentials
    When I send a POST request to "/auth/login" with username "<username>" and password "<password>"
    Then the response status code should be 401
    And the response should contain an error message "Invalid credentials"

    Examples:
      | test_case      | username | password |
      | Empty username |          | password |
      | Empty password  | admin    |          |
