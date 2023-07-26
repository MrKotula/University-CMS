package ua.foxminded.university.entity.enums;

public enum Status {
    NEW("New"), STUDENT("Student"), TEACHER("Teacher");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getStatus() {
        return description.toUpperCase();
    }
}
