package database.todoList.model;

public enum Status {
    PLAN("Plan"), PROCESS("Process"), DONE("Done");

    private String value;

    Status(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}

	public static Status fromString(String value) {
        if (value != null) {
            for (Status status : Status.values()) {
                if (value.equalsIgnoreCase(status.value)) {
                    return status;
                }
            }
        }
        throw new IllegalArgumentException("Enum \"Status\" hasn't such value.");
    }
}
