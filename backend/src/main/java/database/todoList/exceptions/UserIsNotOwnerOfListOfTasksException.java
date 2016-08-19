package database.todoList.exceptions;

public class UserIsNotOwnerOfListOfTasksException extends Exception {
	public UserIsNotOwnerOfListOfTasksException() {
		super("Данный пользователь не может изменять данный список задач, т.к. не является его владельцем.");
	}

	public UserIsNotOwnerOfListOfTasksException(String textOfMessage) {
		super(textOfMessage);
	}
}
