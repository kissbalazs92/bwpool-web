Feature: Customer, Locations, and Tools Menu Usage

  Background:
    Given I open the application in a browser
    Then the application should open

  @firefox @desktop @laptop @tablet @mobile
  Scenario: Recording a Customer and Location in the application
    Given I am on the "Calendar" page
    When I navigate to the "Customer page"
    Then the "Ügyfelek grid" should appear

    Given I request 1 "Customer" data through the API: "https://random-data-api.com/api/users/random_user"
    When I register 1 "Customer" data based on the API message with the following details
      | phone | [blank] |
    And I save the form
    Then the "Customer" data should appear in the grid list

    When I navigate to the "Location page"
    Then the "Telephelyek grid" should appear

    When I register 1 "Location" data based on the API message
    And I save the form
    Then the "Location" data should appear in the grid list

    Given I request 2 "Tool" data through the API: "https://random-data-api.com/api/device/random_device"
    When I navigate to the "Tool page"
    Then the "Eszközök grid" should appear

    When I register 2 "Tool" data based on the API message
    Then the "Tool" data should appear in the grid list

    When I click on the "Excel Export" button
    Then file named "Export.xlsx" should be downloaded

    When I navigate to the "Location page"
    Then the "Telephelyek grid" should appear

    When I filter for the "Location" based on "customerName" in the Search field
    Then the grid should successfully filter for the "Location" with "customerName"

    When I click on the URL in the "Utca" field
    Then the "Location" information page should open

    And I compare the exported file named "Export.xlsx" with the list of "Tools" on the "Location" page
    Then the "Tools" in the file named "Export.xlsx" should match the grid on the "Location" page

    When I navigate to the "Eszközök" page
    Then the "Eszközök" grid should appear

    And I select a "Tool" from the previously recorded "Tools"
    Then the "Tool" should be selected

    When I click on the "Edit" button
    Then the "Tool" data dialog should open

    And I check the "Szervíz" checkbox
    And I save the form
    Then the "Tool" should be marked as "in service" in the grid

    When I navigate to the "Location" page
    And I select a "Location" from the previously recorded "Locations"
    Then the "Location" should be selected

    When I click on the URL in the "Utca" field
    Then the "Location" information page should open