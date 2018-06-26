package com.epam.makedon.agency.agencyweb.domain;

public enum Page {
    INDEX("/", "index"),
    ERROR("/error", "error"),
    USER("/user", "user"),
    TOUR("/tour", "tour"),
    REVIEW("/review", "review"),
    HOTEL("/hotel", "hotel"),
    LOGIN("/login", "login"),
    REGISTRATION("/registration", "registration");

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