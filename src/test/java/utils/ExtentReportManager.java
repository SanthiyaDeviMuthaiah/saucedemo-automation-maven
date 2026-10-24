
package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {

        if (extent == null) {

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

            sparkReporter.config().setDocumentTitle("QA Automation Report");
            sparkReporter.config().setReportName(
                    "UI and API Automation Test Results"
            );

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Project", "AI-Powered QA Automation");
            extent.setSystemInfo("Automation", "Selenium + TestNG");
            extent.setSystemInfo("API Testing", "REST Assured");
        }

        return extent;
    }
}
