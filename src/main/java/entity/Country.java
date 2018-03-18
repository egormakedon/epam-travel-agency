package entity;

public enum Country implements Entity{
    BELARUS(1), RUSSIA(2), POLAND(3), SPAIN(4), ENGLAND(5), UKRAINE(6), USA(7), CHINA(8);

    private long id;

    Country(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}