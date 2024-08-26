/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

package info.rx00405.test.client.tests.features.github;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.apache.commons.validator.routines.UrlValidator;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.function.Executable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.rx00405.test.client.TestClientMain;
import info.rx00405.test.client.utils.GraphQLAPIClient;
import info.rx00405.test.client.utils.aspects.Timed;


public class GithubStepDefinitions {
    private GraphQLAPIClient client = null;
    private JsonObject repoJsonObject = null;

    public void setClient(GraphQLAPIClient newClient) {
        this.client = newClient;
    }

    public GraphQLAPIClient getClient() {
        return this.client;
    }

    public void setRepoJsonObject(JsonObject newRepoJsonObject) {
        this.repoJsonObject = newRepoJsonObject;
    }

    public JsonObject getRepoJsonObject() {
        return this.repoJsonObject;
    }

    @Given("the Github API url of {string}")
    public void the_github_api_url_of(String endpointURL) {
        UrlValidator urlValidator = new UrlValidator();
        assertTrue(urlValidator.isValid(endpointURL));

        setClient(new GraphQLAPIClient());
        getClient().setEndpointURL(endpointURL);
    }

    @Given("the access token is present")
    public void the_access_token_is_present() {
        String currToken = TestClientMain.getConfigOptions().getToken();
        assertNotSame(null, currToken);
        assertNotSame("", currToken);

        getClient().setAccessToken(currToken);
    }

    @Timed
    @When("I check the connection")
    public void i_check_the_connection() {
        GraphQLAPIClient client = getClient();
        Executable exec = () -> client.getSchema();
        assertDoesNotThrow(exec);
        System.out.println("    HTTP Response Status Code: " + client.getResultStatus());
        assertTrue(client.getResultStatus() == 200);
        //System.out.println(client.prettyPrintUsingGson(client.getResultContent()));
    }

    @Then("I should receive a valid GraphQL schema")
    public void i_should_receive_a_valid_graph_ql_schema() {
        String schemaString = getClient().getResultContent();

        JsonElement jsonElement = JsonParser.parseString(schemaString);
        assertTrue(!jsonElement.isJsonNull());

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        assertTrue(jsonObject.has("data"));

        JsonObject dataJsonObject = jsonObject.getAsJsonObject("data");
        assertTrue(dataJsonObject.has("__schema"));

        JsonObject schemaJsonObject = dataJsonObject.getAsJsonObject("__schema");
        assertTrue(schemaJsonObject.has("queryType"));
        assertTrue(schemaJsonObject.has("mutationType"));
        assertTrue(schemaJsonObject.has("types"));
    }

    @Timed
    @When("I fetch the repository information for {string} owned by {string}")
    public void i_fetch_the_repository_information(String repoName, String ownerName) {
        GraphQLAPIClient client = getClient();
        Executable exec = () -> client.getRepoInfo(ownerName, repoName);
        assertDoesNotThrow(exec);
        System.out.println("    HTTP Response Status Code: " + client.getResultStatus());
        assertTrue(client.getResultStatus() == 200);
        //System.out.println(client.prettyPrintUsingGson(client.getResultContent()));
    }

    @Then("I should see the correct repository node")
    public void i_should_see_the_correct_repository_node() {
        String payloadString = getClient().getResultContent();

        JsonElement jsonElement = JsonParser.parseString(payloadString);
        assertTrue(!jsonElement.isJsonNull());

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        assertTrue(jsonObject.has("data"));

        JsonObject dataJsonObject = jsonObject.getAsJsonObject("data");
        assertTrue(dataJsonObject.has("repository"));

        setRepoJsonObject(dataJsonObject.getAsJsonObject("repository"));
        assertTrue(getRepoJsonObject().has("name"));
        assertTrue(getRepoJsonObject().has("description"));
    }

    @Then("I should see the correct repository url")
    public void i_should_see_the_correct_repository_url() {
        assertTrue(getRepoJsonObject().has("url"));
        assertTrue(getRepoJsonObject().get("url").getAsString().contains("java-test-client-template"));
    }

    @Then("I should see that blank issues are allowed")
    public void i_should_see_that_blank_issues_are_allowed() {
        assertTrue(getRepoJsonObject().has("isBlankIssuesEnabled"));
        assertTrue(getRepoJsonObject().get("isBlankIssuesEnabled").getAsBoolean());
    }

    @Then("I should not see that the repository is archived")
    public void i_should_not_see_that_the_repository_is_archived() {
        assertTrue(getRepoJsonObject().has("isArchived"));
        assertFalse(getRepoJsonObject().get("isArchived").getAsBoolean());
    }

    @Then("I should not see that the repository is disabled")
    public void i_should_not_see_that_the_repository_is_disabled() {
        assertTrue(getRepoJsonObject().has("isDisabled"));
        assertFalse(getRepoJsonObject().get("isDisabled").getAsBoolean());
    }

    @Then("I should not see that the repository is locked")
    public void i_should_not_see_that_the_repository_is_locked() {
        assertTrue(getRepoJsonObject().has("isLocked"));
        assertFalse(getRepoJsonObject().get("isLocked").getAsBoolean());
    }

    @Then("I should not see that the repository is empty")
    public void i_should_not_see_that_the_repository_is_empty() {
        assertTrue(getRepoJsonObject().has("isEmpty"));
        assertFalse(getRepoJsonObject().get("isEmpty").getAsBoolean());
    }

    @Then("I should not see that the repository is a fork")
    public void i_should_not_see_that_the_repository_is_a_fork() {
        assertTrue(getRepoJsonObject().has("isFork"));
        assertFalse(getRepoJsonObject().get("isFork").getAsBoolean());
    }

    @Then("I should not see that the repository is a mirror")
    public void i_should_not_see_that_the_repository_is_a_mirror() {
        assertTrue(getRepoJsonObject().has("isMirror"));
        assertFalse(getRepoJsonObject().get("isMirror").getAsBoolean());
    }
}
