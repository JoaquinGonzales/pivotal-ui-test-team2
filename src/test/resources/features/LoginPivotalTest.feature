Feature: Testing for login Pivotal page

  Scenario: Login with valid credentials

    Given I login with credentials valid
    Then It is expected that the display name equals HENRRYS