package ua.foxminded.university.entity.enums;

public enum RegistrationStatus {
    NEW("New"), REGISTRATED("Student");

    private String strStatus;

    private RegistrationStatus(String strStatus) {
	this.strStatus = strStatus;
    }

    public String getRegistrationStatus() {
	return strStatus.toUpperCase();
    }
}
