Feature: OAuth
  As Retail Customer, I should be able to see Data Custodian list

  Scenario: Retail Customer views Data Custodian list
    Given a logged in Retail Customer

    When I navigate to Data Custodian list
    Then I should see a list of Data Custodians

  @close
  Scenario: Retail Customer selects Data Custodian for the list
    Given a logged in Retail Customer

    When I select a Data Custodian from the list
    Then I should see the Data Custodian login screen

    When I log into the Data Custodian
