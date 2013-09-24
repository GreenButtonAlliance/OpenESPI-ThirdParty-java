Feature: Authentication
  Retail Customers should be able to login

  Scenario: Retail Customer visits Retail Customer's home page
    Given I have a Retail Customer account

    When I log in as Alan Turing
    Then I should see Retail Customer home page
