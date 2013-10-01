Feature: Home Page
  As a Retail Customer
  I want to view the home page
  So that I can see the Third Party web site working

  Scenario: Retail Customer visits the home page
    Given I am a Retail Customer

    When I visit the home page
    Then I should see a welcome message

  Scenario: Visitor views the Terms of Service
    When I visit the Terms of Service page
    Then I should see the Terms of Service

  Scenario: Visitor views the Usage Policy
    When I visit the Usage Policy page
    Then I should see the Usage Policy
