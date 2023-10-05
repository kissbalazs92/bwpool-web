package stepdefinitions;

import api.TestDataCreator;
import io.cucumber.java.en.Given;
import models.CustomerModel;
import models.ToolModel;
import utils.ScenarioContext;

import java.util.List;

public class ApiSteps extends StepDefinitionBase {

    public ApiSteps(ScenarioContext context) {
        super(context);
    }

    @Given("I request {int} {string} through the API: {string}")
    public void iRequestUserDataThroughTheAPI(int count, String modelName, String url) {
        TestDataCreator testDataCreator = new TestDataCreator();
        url = url + "?size=" + count;
        if (modelName.toLowerCase().contains("customer")) {
            List<CustomerModel> costumers = testDataCreator.getRandomItems(url, CustomerModel[].class);
            context.setLatestCostumers(costumers);
        } else if (modelName.toLowerCase().contains("tool")) {
            List<ToolModel> toolModels = testDataCreator.getRandomItems(url, ToolModel[].class);
            context.setLatestTools(toolModels);
        }
    }
}
