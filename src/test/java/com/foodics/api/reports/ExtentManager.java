package com.foodics.api.reports;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
public class ExtentManager {

    private static ExtentReports extent;
    private static String reportFileName = "FoodicsAPIReport"+ ".html";
    private static String reportFilepath = "resources/report/";
    private static String reportFileLocation = reportFilepath  + reportFileName;


    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {

        extent = new ExtentReports();
        extent.setAnalysisStrategy(AnalysisStrategy.CLASS);

        String fileName = getReportPath(reportFilepath);
        System.out.println(fileName);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent.attachReporter(htmlReporter);

        return extent;
    }

    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                return reportFileLocation;
            } else {
                return System.getProperty("user.dir");
            }
        } else {
        }
        return reportFileLocation;
    }

}
