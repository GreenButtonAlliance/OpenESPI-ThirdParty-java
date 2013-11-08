Feature: Retail Customers
  As a Retail Customer
  I want to be able to log in to the Third Party web site
  So that I can see my retail customer home page

  Scenario: Retail Customer logs in with valid credentials
    Given I have a Retail Customer account

    When I log in as Alan Turing
    Then I should be logged in

  Scenario: Retail Customer logs in with invalid credentials
    Given I have a Retail Customer account

    When I log in with invalid credentials
    Then I should see the login form

  Scenario: Logged in Retail Customer access unauthorized page
    Given I have a Retail Customer account
    And I log in as Alan Turing

    When I attempt to view custodian/home

    Then I should see an unauthorized screen