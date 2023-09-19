package com.medhat.springboot.courseswebsite.utils;

public class Constants {

    public static final int COURSE_PAGE_SIZE=15; // todo should be taken from frontend
    public static final Integer DEFAULT_NEW_USER_ROLE=3; // todo should be taken applied if it comes null from frontend

    public static final Integer DEFAULT_NEW_USER_ENABLED=1;

    private static final Integer JWT_TOKEN_VALID_TIME_IN_MINUTES = 360;
    public static final int JWT_TOKEN_VALID_DURATION = 60 * JWT_TOKEN_VALID_TIME_IN_MINUTES;
}