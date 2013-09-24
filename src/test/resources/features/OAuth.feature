Feature: OAuth
  As Retail Customer, I should be able to see Data Custodian list

  Scenario: Retail Customer views Data Custodian list
    Given a logged in Retail Customer

    When I navigate to Data Custodian list
    Then I should see a list of Data Custodians
