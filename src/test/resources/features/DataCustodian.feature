Feature: Data Custodian
  As a Data Custodian
  I want to be able to create retail customers
  So that I can associate retail customers to their usage data

  Scenario: Data Custodian visits Data Custodian's home page
    Given I have a Data Custodian account

    When I log in as a Data Custodian
    Then I should see Data Custodian home page

  Scenario: Data Custodian logs in with valid credentials
    Given I have a Data Custodian account

    When I log in as a Data Custodian
    Then I should be logged in

  Scenario: User logs in with invalid credentials
    Given I have a Data Custodian account

    When I log in as a Data Custodian with invalid credentials
    Then I should see login form

  Scenario: Data Custodian creates customer
    Given I am a Data Custodian

    When I log in as a Data Custodian
    And I create a new Retail Customer
    Then I should see the new Retail Customer in the customer list

  Scenario: Data Custodian views customer list
    Given I am a Data Custodian
    And Alan Turing is a Retail Customer
    And I log in as a Data Custodian

    When I navigate to customer list page
    Then I should see Alan Turing in the customer list
