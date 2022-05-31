Feature: AS A admin I WANT TO login SO THAT i can access the platform 

    Scenario: Search statistics of USA for the last year

        When I access 'https://blazedemo.com/'
        Then page title should be "Covid-19"
        And I want to search statistics from "USA"
        And I search for the "last year"
        Then country title should be "USA"
        And statitic differential text should be last "356 days"

    Scenario: Search statistics of USA for the last month

        When I access 'https://blazedemo.com/'
        Then page title should be "Covid-19"
        And I want to search statistics from "USA"
        And I search for the "last month"
        Then country title should be "USA"
        And statitic differential text should be last "30 days"