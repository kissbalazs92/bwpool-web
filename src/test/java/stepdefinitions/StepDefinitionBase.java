package stepdefinitions;

import utils.ScenarioContext;

public abstract class StepDefinitionBase {

    protected ScenarioContext context;

    public StepDefinitionBase(ScenarioContext context) {
        this.context = context;
    }
}

