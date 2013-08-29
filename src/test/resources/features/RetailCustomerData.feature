Feature: Retail Customer Data
  As a Retail Customer
  I want to be able to view my data in my browser
  So that I can see my data

  Scenario: Retail Customer downloads Usage Points in XML format
    Given a logged in retail customer

    When I look at my usage page
    Then I should be able to download Usage Points in XML format