Feature: Home Page
  As a Retail Customer
  I want to view the home page
  So that I can see the Third Party web site working

  Scenario: Retail Customer visits the home page
    Given I am a Retail Customer

    When I visit the home page
    Then I should see a welcome message
