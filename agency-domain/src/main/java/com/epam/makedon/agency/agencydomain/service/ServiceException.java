package com.epam.makedon.agency.agencydomain.service;

/**
 * This class define Service exception,
 * which can be thrown from {@link com.epam.makedon.agency.agencydomain.service} package,
 * extends {@link RuntimeException} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}