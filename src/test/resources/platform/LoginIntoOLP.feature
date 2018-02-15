Feature: log into OLP

  Scenario: log via browser
    When i navigate to olp page
    And i fill the login inputs
    And i click the sign in button
    Then I should be logged correctly