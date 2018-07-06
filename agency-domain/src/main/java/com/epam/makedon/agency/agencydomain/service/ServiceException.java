package com.epam.makedon.agency.agencydomain.service;

/**
 * Class ServiceException extends RuntimeException
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}