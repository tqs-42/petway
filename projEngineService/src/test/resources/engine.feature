Feature: Engine Service - Login and registration

    Scenario: Login as admin

        When I access 'https://localhost:4200/login'
        Then page title should be "Login"
        And I fill the email with "admin@ua.pt" and password with "admin123"
        And I click the login button
        Then I should be in the main page

    Scenario: Login as rider

        When I access 'https://localhost:4200/login'
        Then page title should be "Login"
        And I fill the email with "rider@ua.pt" and password with "rider123"
        And I click the login button
        Then I should be in the main page

    Scenario: Invalid login

        When I access 'https://localhost:4200/login'
        Then page title should be "Login"
        And I fill the email with "abcdefg@xyz.com" and password with "aaaaaaa"
        And I click the login button
        Then It should show an error

    Scenario: Registrate as rider (valid)

        When I access 'https://localhost:4200/register'
        Then page title should be "Rider Registration"
        And I fill the email with "admin@ua.pt" and password with "rider123"
        And I click the register button
        Then I should be in the main page

    Scenario: Registrate as rider (invalid)

        When I access 'https://localhost:4200/register'
        Then page title should be "Rider Registration"
        And I fill the email with "admin" and password with "rider123"
        And I click the register button
        Then It should show an error