package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

public class ExtentReport {
    public static ExtentReports extentReports = null;
    public static ExtentTest extentlog;

    public static void initialize(String extentConfigxmlPath) {

        if(extentReports == null) {
            extentReports = new ExtentReports(extentConfigxmlPath, true);
            extentReports.addSystemInfo("Host Name", System.getProperty("user.name"));
            extentReports.addSystemInfo("Environment", "QA");
            extentReports.addSystemInfo("OS", "Windows");
            extentReports.loadConfig(new File(System.getProperty("user.dir")+"/resources/extent-config.xml"));
        }

    }
}
