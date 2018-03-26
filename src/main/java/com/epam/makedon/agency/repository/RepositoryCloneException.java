package com.epam.makedon.agency.repository;

public class RepositoryCloneException extends CloneNotSupportedException {
    public RepositoryCloneException() {
        super();
    }

    public RepositoryCloneException(String s) {
        super(s);
    }
}
