package ua.foxminded.university.entity.enums;

public enum RoleModel {
    ADMIN("Admin"), MODERATOR("Moderator"), STUDENT("Student"), TEACHER("Teacher"), USER("User");

    private final String description;

    private RoleModel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description.toUpperCase();
    }
}
