package ua.foxminded.university.entity.enums;

public enum Degree {
    ASSOCIATE("Associate degree"), BACHELOR("Bachelor’s degree"), MASTER("Master’s degree"),
    DOCTORAL("Doctoral degree"), PROFESSIONAL("Professional degree");

    private final String description;

    private Degree(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
