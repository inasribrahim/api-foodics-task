package com.foodics.api.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.text.MessageFormat;

public class ExtentTestManager {

    static ExtentReports extent = ExtentManager.getInstance();

    public static synchronized ExtentTest getTest() {
        return ExtentReporter
                .get((int) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String module, String testCase, String... params) {
        ExtentTest extentModule = ExtentModuleManager.getModule(module);
        String testCaseDescription = MessageFormat.format(testCase, params);
        ExtentTest test = extentModule.createNode(testCaseDescription);
        ExtentReporter.set((int) (Thread.currentThread().getId()), test);

        return test;
    }

    public static synchronized void endTest() {
        extent.flush();
    }


    public static synchronized void step() {
        ExtentTest test = getTest();
    }

}
