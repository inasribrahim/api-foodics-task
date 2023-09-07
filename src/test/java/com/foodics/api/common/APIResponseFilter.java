package com.foodics.api.common;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.foodics.api.reports.ExtentTestManager;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.PrintStream;
import java.io.StringWriter;
import org.apache.commons.io.output.WriterOutputStream;

public class APIResponseFilter implements Filter {
    public static Markup markup;

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        String responseStr = ResponsePrinter.print(response,
                response,
                new PrintStream(new WriterOutputStream(new StringWriter())),
                LogDetail.ALL,
                true
        );

        String requestStr = RequestPrinter.print(filterableRequestSpecification,
                filterableRequestSpecification.getMethod(),
                filterableRequestSpecification.getURI(),
                LogDetail.ALL,
                new PrintStream(new WriterOutputStream(new StringWriter())),
                true);

        markup = MarkupHelper.createCodeBlock(requestStr, responseStr);
        ExtentTestManager.getTest().log(Status.INFO, markup);
        return response;
    }

    public static Markup getMarkupStatus() {
        return markup;
    }
}
