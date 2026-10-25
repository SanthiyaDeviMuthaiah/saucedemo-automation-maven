
package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    private static final ExtentReports extent =
            ExtentReportManager.getReportInstance();

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
        test.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        test.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
        test.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTest summary = extent.createTest("Test Suite Summary");

        summary.info("Suite: " + context.getName());
        summary.info("Passed tests: " + context.getPassedTests().size());
        summary.info("Failed tests: " + context.getFailedTests().size());
        summary.info("Skipped tests: " + context.getSkippedTests().size());

        extent.flush();
    }
}
