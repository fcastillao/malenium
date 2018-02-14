Feature: test notebook behavior

  Scenario: load notebook
    Given that i am logged in zeppelin
    When i open the notebook called Build a Graph
    Then the notebook loads correctly
    Then close the driver