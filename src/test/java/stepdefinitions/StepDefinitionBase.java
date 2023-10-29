package stepdefinitions;

import org.testng.asserts.SoftAssert;
import utils.ScenarioContext;

public abstract class StepDefinitionBase {

    protected ScenarioContext context;
    protected SoftAssert softAssert = new SoftAssert();

    public StepDefinitionBase(ScenarioContext context) {
        this.context = context;
    }
}

