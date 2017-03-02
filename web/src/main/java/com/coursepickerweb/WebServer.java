package com.coursepickerweb;

import static spark.Spark.*;
import com.coursepicker.courseplanner.*;
import java.util.*;

public class WebServer {

    private int mPortNumber;

    public WebServer(final int portNumber) {
        mPortNumber = portNumber;

    }

    public void start() {
        port(mPortNumber);
        staticFiles.location("/public");
        setupIndexPage();
        setupRESTfulRequests();
    }

    public void setupIndexPage() {
        get("/", (request, response) -> {
            response.redirect("html/index.html");
            return null;
        });
    }
    private void setupRESTfulRequests() {
        get("/grade", (request, response) -> {
            try {
                String requestedCourse = request.queryParams("course");
                int requestedYears = Integer.parseInt(request.queryParams("years"));

                List<PastCourse> courses = GradeGetter.getInstance().getCourse(requestedCourse, requestedYears, true);
                StringBuilder jsonBuilder = new StringBuilder();
                jsonBuilder.append("[\n");
                for (PastCourse course: courses) {
                    jsonBuilder.append("    {\n");
                    jsonBuilder.append("        \"course\": \"" + course.getCourse() + "\",\n");
                    jsonBuilder.append("        \"instructor\": \"" + course.getInstructor() + "\",\n");
                    jsonBuilder.append("        \"A\": " + course.getA() + ",\n");
                    jsonBuilder.append("        \"B\": " + course.getB() + ",\n");
                    jsonBuilder.append("        \"C\": " + course.getC() + ",\n");
                    jsonBuilder.append("        \"D\": " + course.getD() + ",\n");
                    jsonBuilder.append("        \"F\": " + course.getF() + "\n");
                    jsonBuilder.append("    },\n");
                }
                jsonBuilder.deleteCharAt(jsonBuilder.length()-2);
                jsonBuilder.append("\n]");

                response.type("application/json");
                return jsonBuilder.toString();
            } catch (Exception e) {
                System.out.println(e);
                return "Bad request";
            }
        });
    }
}