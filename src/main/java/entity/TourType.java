package entity;

public enum TourType {
    CHILDREN(1), WEEKEND(2), WEDDING(3), SHOPING(4), EXCURSION(5);

    private long id;

    TourType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
