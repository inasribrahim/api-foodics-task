package com.foodics.api.tests;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.foodics.api.reports.ExtentManager;
import com.foodics.api.reports.ExtentTestManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
    private String insertSpaceAfterUpperCaseAndThenConvertItToLowerCase(String str){
        return str.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2").toLowerCase();
    }

    @AfterSuite
    public void flushReport(){
        ExtentManager.getInstance().flush();
    }

    @AfterMethod
    public void getResult(ITestResult result){
        if(result.getStatus() ==ITestResult.SUCCESS){
            ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createLabel(result.getTestName(), ExtentColor.GREEN));
        } else if (result.getStatus() ==ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getTestName(), ExtentColor.RED));
        } else if (result.getStatus() ==ITestResult.SKIP) {
            ExtentTestManager.getTest().log(Status.SKIP, MarkupHelper.createLabel(result.getTestName(), ExtentColor.INDIGO));
        }
    }
    @BeforeMethod
    public void beforeTest(Method method){
        ExtentTestManager.startTest(method.getName(),insertSpaceAfterUpperCaseAndThenConvertItToLowerCase(method.getName()) );
    }
}
