package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGITestListener implements ITestListener {

    private static final ExtentReports extent = new ExtentReports();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        extent.attachReporter(new ExtentSparkReporter("extent.html")); // Jelentés fájl neve
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite is ending!");
        extent.flush(); // Menti a jelentést
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Egyedi nevet adhatsz a tesztnek itt
        String methodName = result.getMethod().getMethodName();
        String descriptiveTestName = methodName + " with some extra info";
        System.out.println(descriptiveTestName + " started!");
        // Létrehoz egy tesztesetet az egyedi névvel
        test.set(extent.createTest(descriptiveTestName));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " passed!");
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " failed!");
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " skipped!");
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
    }

    public void setTagsForTests(ITestContext context){

    }
}

