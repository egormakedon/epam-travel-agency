package com.epam.makedon.agency.agencyweb.security;

/**
 * Service for Security
 *
 * @author Yahor Makedon
 * @version 1.0
 */
public interface SecurityService {

    String findLoggedInUsername();
    void autoLogin(String username, String password);
}