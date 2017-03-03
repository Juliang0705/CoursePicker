package com.coursepickerweb.server;

import static spark.Spark.*;
import com.coursepicker.courseplanner.*;
import com.coursepicker.courseplanner.schedulemaker.*;
import com.coursepickerweb.utils.JSONConverter;
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
            response.redirect("index.html");
            return null;
        });
    }

    private void setupRESTfulRequests() {
        setupGetGradeRequest();
        setupGetScheduleRequest();
    }

    /**
     * sample request
     * http://localhost:8880/grade?course=CSCE121&years=3
     */
    private void setupGetGradeRequest() {
        get("/grade", (request, response) -> {
            try {
                String requestedCourse = request.queryParams("course");
                int requestedYears = Integer.parseInt(request.queryParams("years"));

                List<PastCourse> pastCourseList = GradeGetter.getInstance().getCourse(requestedCourse, requestedYears, true);
                response.type("application/json");
                return JSONConverter.serialize(pastCourseList);
            } catch (Exception e) {
                e.printStackTrace();
                //for debugging purpose, send the exception to the front end
                return e.toString();
            }
        });
    }

    /**
     * sample request
     * http://localhost:8880/schedule?year=2017&semester=spring&course=CSCE&number=121
     */
    private void setupGetScheduleRequest() {
        get("/schedule", (request, response) -> {
            try {
                String requestedYear = request.queryParams("year");
                String requestedSemester = request.queryParams("semester");
                String requestedCourse = request.queryParams("course").toUpperCase();
                String requestedNumber = request.queryParams("number");

                ScheduleDataGetter.Semester semester = null;
                if (requestedSemester.equalsIgnoreCase("spring")) {
                    semester = ScheduleDataGetter.Semester.SPRING;
                } else if (requestedSemester.equalsIgnoreCase("summer")) {
                    semester = ScheduleDataGetter.Semester.SUMMER;
                } else if (requestedSemester.equalsIgnoreCase("fall")) {
                    semester = ScheduleDataGetter.Semester.FALL;
                } else {
                    throw new IllegalArgumentException("Undefined semester");
                }
                List<FutureCourse> futureCourseList = SchedulePlanner.getInstance()
                                                                     .getCourse(requestedYear, semester, requestedCourse, requestedNumber);
                response.type("application/json");
                return JSONConverter.serialize(futureCourseList);
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        });
    }
}