Feature:
    To perform a get request on the PetStore API based on the Pet Status
    validate the response payload for the status and elements for the data provided

    Background: Setup API service URL based on the status available
        Given The Pet Store API service is available
        Then I set the appropriate URL for the service

    Scenario: Verify PetStore API response for the status and pets name
      Given I request PetStore application for the status data below
        | Status    |
        | available |
      Then I verify the response for the Status , Pet name and Pet count for the data below
        | Status    | PetName | PetCount |
        | available | doggie  | 3176     |