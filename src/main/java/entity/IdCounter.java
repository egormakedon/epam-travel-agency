package entity;

public final class IdCounter {
    private IdCounter() {}

    public static long incrementId(long id) {
        return ++id;
    }
}