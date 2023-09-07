package com.foodics.api.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentModuleManager {

    static Map<String, ExtentTest> extentModulesMap = new HashMap<String, ExtentTest>();
    static ExtentReports extent = ExtentManager.getInstance();

    public static synchronized ExtentTest getModule(String module) {

        ExtentTest currentThread = extentModulesMap.get(module);

        if (currentThread == null) {
            return createModule(module);
        } else {
            return currentThread;
        }

    }

    private static synchronized ExtentTest createModule(String module) {
        ExtentTest test = extent.createTest(module);
        extentModulesMap.put(module, test);
        return test;
    }

}
