package com.epam.makedon.agency.agencyweb.util;

/**
 * This class define pages and there url's.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public enum Page {

    // public
    INDEX("/", "public/index"),
    ERROR("/error", "public/error"),
    LOGIN("/login", "public/login"),
    REGISTRATION("/registration", "public/registration"),
    TOUR("/tour", "public/tour"),

    //admin
    ADMIN("/admin", "admin/admin"),
    HOTEL("/hotel", "admin/hotel"),

    USER("/user", "user"),
    REVIEW("/review", "review");

    private String url;
    private String page;

    Page(String url, String page) {
        this.url = url;
        this.page = page;
    }

    public String getUrl() {
        return url;
    }
    public String getPage() {
        return page;
    }
}