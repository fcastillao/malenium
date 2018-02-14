Feature: B - Configure customer with personalized configuration

  Background:
    Given The user have a repository configured correctly

  Scenario: Log-in into the platform
    When The user logs in into platform page
    Then the preconfigured interpreters are loaded
    And the preconfigured notebooks are loaded
    And the preconfigured repositories are loaded
    And the preconfigured interpreter has the sparkUI configured

  Scenario: Run existing notebooks
    When I open a notebook
    And I run a paragraph of type <paragraph type>
    Then It run correctly (expected output)
  Examples:
  | paragraph type   |
  | Angular          |
  | Spark            |
  | Python           |
  | RSpark           |
  | WithDependencies |
	