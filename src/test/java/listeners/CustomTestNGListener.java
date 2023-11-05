package listeners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCaseFinished;
import utils.ExtentManager;

public class CustomTestNGListener implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        Result result = event.getResult();
        Status status = result.getStatus();
        if(status == Status.FAILED) {
            Throwable error = result.getError();
            StackTraceElement[] stackTraceElements = error.getStackTrace();
            String errorMessage = getErrorMessage(error, stackTraceElements);
            ExtentManager.test.log(com.aventstack.extentreports.Status.FAIL, errorMessage);
        }
    }

    public String getErrorMessage(Throwable error, StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder();
        sb.append(error).append("\n");
        for (StackTraceElement element : stackTraceElements) {
            sb.append("\tat ").append(element).append("\n");
        }
        return sb.toString();
    }
}