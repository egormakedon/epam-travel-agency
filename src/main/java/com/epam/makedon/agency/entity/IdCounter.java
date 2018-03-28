package com.epam.makedon.agency.entity;

/**
 * Class {@code IdCounter} is final class.
 * It has only one method, which increment input id.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 1.0
 * @since version 1.0
 */
public final class IdCounter {
    private IdCounter() {}

    /**
     * incrementId is a public static method, which calculate id.
     *
     * @param id long variable
     * @return id increment of input param
     */
    public static long incrementId(Long id) {
        return ++id;
    }
}