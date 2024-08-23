package info.rx00405.test.client.tests.features.github;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import io.cucumber.plugin.event.Result;

//import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;
//import org.junit.jupiter.api.Test;

//import com.google.common.graph.Graph;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.function.Executable;

import info.rx00405.test.client.TestClientMain;
import info.rx00405.test.client.utils.GraphQLAPIClient;
import info.rx00405.test.client.utils.aspects.Timed;


public class GithubStepDefinitions {
    private GraphQLAPIClient client = null;
    //private String endpointURL = "";
    //private String accessToken = "";

    public void setClient(GraphQLAPIClient newClient) {
        this.client = newClient;
    }

    public GraphQLAPIClient getClient() {
        return this.client;
    }

    // public void setAccessToken(String token) {
    //     this.accessToken = token;
    // }

    // public String getAccessToken() {
    //     return accessToken;
    // }

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
        assertTrue(client.getResultStatus() == 200);
        //System.out.println(client.prettyPrintUsingGson(client.getResultContent()));
    }

    @Then("I should receive a valid GraphQL schema")
    public void i_should_receive_a_valid_graph_ql_schema() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I fetch the repository information")
    public void i_fetch_the_repository_information() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see the correct repository node")
    public void i_should_see_the_correct_repository_node() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see the correct repository owner")
    public void i_should_see_the_correct_repository_owner() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see the correct repository url")
    public void i_should_see_the_correct_repository_url() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see that blank issues are allowed")
    public void i_should_see_that_blank_issues_are_allowed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should not see that the repository is archived")
    public void i_should_not_see_that_the_repository_is_archived() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should not see that the repository is disabled")
    public void i_should_not_see_that_the_repository_is_disabled() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should not see that the repository is locked")
    public void i_should_not_see_that_the_repository_is_locked() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should not see that the repository is empty")
    public void i_should_not_see_that_the_repository_is_empty() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should not see that the repository is a fork")
    public void i_should_not_see_that_the_repository_is_a_fork() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should not see that the repository is a mirror")
    public void i_should_not_see_that_the_repository_is_a_mirror() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
