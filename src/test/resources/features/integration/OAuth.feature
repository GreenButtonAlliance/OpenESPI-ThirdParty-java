@oauth
Feature: OAuth
  As Retail Customer, I should be able to see Data Custodian list

  Scenario: Retail Customer views Data Custodian list
    Given a logged in Retail Customer

    When I navigate to Data Custodian list
    Then I should see a list of Data Custodians

  Scenario: Retail Customer selects Data Custodian for the list
    Given a logged in Retail Customer

    When I select a Data Custodian from the list
    Then I should see the Data Custodian login screen

  Scenario: Retail Customer selects Scope
    Given a logged in Retail Customer

    When I select a Data Custodian from the list
    Then I should see the Data Custodian login screen

    When I log into Data Custodian
    Then I should see Scope selection screen

  Scenario: Retail Customer authorizes Third Party
    Given a logged in Retail Customer

    When I select a Data Custodian from the list
    Then I should see the Data Custodian login screen

    When I log into Data Custodian
    Then I should see Scope selection screen

    When I select Scopes
    Then I should see authorization screen

    When I authorize Third Party
    Then I should see all my authorizations
