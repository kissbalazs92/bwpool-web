Feature: Customer, Locations, and Tools Menu Usage

  Background:
    Given I open the application in a browser
    Then the application should open

  @firefox @chrome @desktop @laptop @tablet @mobile
  Scenario: Recording a Customer and Location in the application
    Given I am on the "Calendar" page
    When I navigate to the "Customer page"
    Then the "Ügyfelek grid" should appear