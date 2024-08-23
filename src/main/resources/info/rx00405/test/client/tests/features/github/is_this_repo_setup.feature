###
# Copyright (c) 2024 Battle Road Consulting. All rights reserved.
###

Feature: Is This Repo Setup Correctly?
  Everybody wants to know if the repo is set up correctly

  Scenario: Check connectivity to Github
    Given the Github API url of "https://api.github.com/graphql"
    And the access token is present
    When I check the connection
    Then I should receive a valid GraphQL schema

  Scenario: Check the condition of the repository on Github
    Given the Github API url
    And the access token is present
    When I fetch the repository information
    Then I should see the correct repository node
    And I should see the correct repository owner
    And I should see the correct repository url
    And I should see that blank issues are allowed
    But I should not see that the repository is archived
    And I should not see that the repository is disabled
    And I should not see that the repository is locked
    And I should not see that the repository is empty
    And I should not see that the repository is a fork
    And I should not see that the repository is a mirror
