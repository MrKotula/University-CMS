package ua.foxminded.university.entity.enums;

public enum RegistrationStatus {
    NEW("New"), REGISTERED("Registered");

    private final String description;

    private RegistrationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description.toUpperCase();
    }
}
