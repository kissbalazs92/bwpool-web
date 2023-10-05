Feature: Customer and Locations Menu Usage

  Scenario: Recording a Customer and Location in the application
    Given I open the application in a browser
    Then the application should open

    When I navigate to the "Partnerek" page
    Then the "Ügyfelek" grid should appear

    Given I request 1 "user data" through the API: "https://random-data-api.com/api/users/random_user"
    When I register 1 "partner" based on the API message
    And I save the form
    Then the "partner" should appear in the grid list

    When I navigate to the "Telephelyek" page
    Then the "Telephelyek" grid should appear

    When I register 1 "site data" based on the API message
    And I save the form
    Then the "site" should appear in the grid list

    Given I request 2 "data of devices" through the API: "https://random-data-api.com/api/device/random_device"
    When I navigate to the "Eszközök" page
    Then the "Eszközök" grid should appear
