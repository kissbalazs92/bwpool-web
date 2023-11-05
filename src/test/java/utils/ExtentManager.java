package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

    public static ExtentReports extent;
    public static ExtentTest test;

    public static void setupExtendReport() {
        String reportPath = Configurations.getReportPath();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

}
