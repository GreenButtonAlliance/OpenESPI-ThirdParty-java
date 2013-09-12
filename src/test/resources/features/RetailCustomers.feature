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

  Scenario: Retail Customer views Usage Points
    Given I have a Retail Customer account

    When I log in as Alan Turing
    And I look at my Usage Points page
    Then I should see Usage Point with title "Front Electric Meter"

  Scenario: Retail Customer views Usage Points with Meter Readings
    Given I have a Retail Customer account

    When I log in as Alan Turing
    And I look at my Usage Points page
    And I select Usage Point
    And I select Meter Reading

    Then I should see Meter Reading
    And I should see Reading Type
