Feature: Customer and Locations Menu Usage

  Scenario: Recording a Customer and Location in the application
    Given I open the application in a browser
    Then the application should open

    When I navigate to the "Customer" page
    Then the "Ügyfelek" grid should appear

    Given I request 1 "Customer data" through the API: "https://random-data-api.com/api/users/random_user"
    When I register 1 "Customer" based on the API message with the following details
      | phone | [blank] |
    And I save the form
    Then the "Customer" should appear in the grid list

    When I navigate to the "Location" page
    Then the "Telephelyek" grid should appear

    When I register 1 "Location data" based on the API message
    And I save the form
    Then the "Location" should appear in the grid list

    Given I request 2 "data of Tools" through the API: "https://random-data-api.com/api/device/random_device"
    When I navigate to the "Tool" page
    Then the "Eszközök" grid should appear
