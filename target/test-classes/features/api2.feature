Feature: GETPosts
  Verify different GET operations using REST-assured

  Scenario: Verify one author of the post
    Given I perform GET operation for "/posts"
#    Then I should see the author name as "Karthik KK"