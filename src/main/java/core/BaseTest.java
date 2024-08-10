package core;

import com.relevantcodes.extentreports.LogStatus;
import helper.BaseTestHelper;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ExtentReport;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void config(){
        //Storing all the extent report in particular folder
        String subFolderPath = System.getProperty("user.dir")+"/reports/" + BaseTestHelper.Timestamp();
        BaseTestHelper.CreateFolder(subFolderPath);
        ExtentReport.initialize(subFolderPath+"/"+"API_Execution_Automation.html");

    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) {

        if(result.getStatus() == ITestResult.SUCCESS) {
            ExtentReport.extentlog.log(LogStatus.PASS, "Test Case: "+result.getName()+" is passed.");
        } else if(result.getStatus() == ITestResult.FAILURE) {
            ExtentReport.extentlog.log(LogStatus.FAIL, "Test Case: "+result.getName()+" is failed.");
            ExtentReport.extentlog.log(LogStatus.FAIL, "The case is failed due to: "+result.getThrowable());
        } else if(result.getStatus() == ITestResult.SKIP) {
            ExtentReport.extentlog.log(LogStatus.SKIP, "Test Case: "+result.getName()+" is skipped.");
        }
        ExtentReport.extentReports.endTest(ExtentReport.extentlog);
    }

    @AfterSuite(alwaysRun = true)
    public void endReport() {
        ExtentReport.extentReports.close();
    }
}
