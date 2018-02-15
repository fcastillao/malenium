Feature: A - Configure customer with default configurations

  Scenario: Log-in into the platform
    When The user logs in into platform page
    Then the default interpreters are loaded
    And the default notebooks are loaded
    And the default repositories are loaded

  Scenario: Add a new repository
    When the user adds a new repository
    Then the repository appears in the interface
    And the new configuration is uploaded to S3

  Scenario Outline: Run existing notebooks
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

  Scenario: Edit the Spark UI and open the spark UI from zeppelin
    When the user edits the spark interpreter
    And modify the spark UI link
    Then link is correctly saved
    And the new configuration is uploaded to S3

  Scenario Outline: Create and run a new notebook
    When the user creates a new notebook
    And the user runs the notebook
    And I run a paragraph of type <paragraph type>
    Then It run correctly (expected output)
    Examples:
      | paragraph type   |
      | Angular          |
      | Spark            |
      | Python           |
      | RSpark           |
      | WithDependencies |

  Scenario Outline: Import and run a Notebook
    When the user imports a new notebook using the following methods
      | method      |
      | json method |
      | nurl method |
    And the user runs the notebook
    And I run a paragraph of type <paragraph type>
    Then It run correctly (expected output)
    Examples:
      | paragraph type   |
      | Angular          |
      | Spark            |
      | Python           |
      | RSpark           |
      | WithDependencies |

  Scenario: Delete a Notebook/Folder
    When the user removes the notebooks
    Then verify the notebook is deleted correctly
