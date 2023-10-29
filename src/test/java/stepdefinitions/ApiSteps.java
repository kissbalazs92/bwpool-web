package stepdefinitions;

import api.TestDataCreator;
import enums.DataType;
import io.cucumber.java.en.Given;
import models.CustomerModel;
import models.ToolModel;
import utils.ScenarioContext;

import java.util.List;

public class ApiSteps extends StepDefinitionBase {

    public ApiSteps(ScenarioContext context) {
        super(context);
    }

    @Given("I request {int} \"{dataType}\" data through the API: {string}")
    public void iRequestDataThroughTheAPI(int count, DataType modelName, String url) throws Exception {
        TestDataCreator testDataCreator = new TestDataCreator();
        url = url + "?size=" + count;
        switch (modelName) {
            case CUSTOMER:
                List<CustomerModel> customers = testDataCreator.getRandomItems(url, CustomerModel[].class);
                context.setLatestCustomers(customers);
                break;
            case TOOL:
                List<ToolModel> toolModels = testDataCreator.getRandomItems(url, ToolModel[].class);
                context.setLatestTools(toolModels);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + modelName);
        }
    }

}
