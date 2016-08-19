package database.todoList.services;

import database.todoList.model.Task;

import java.util.Collection;

public interface TaskService {
	void insertTask(Task task);
	void insertTasks(Collection<Task> tasks);

	Task findTaskByGuid(String guidOfTask);
	Collection<Task> findAllTasksOfListOfTasks(String guidOfListOfTasks);
	int findCountOfTasks();

	void updateTask(Task task);

	void deleteTask(String guidOfTask);
	void deleteTasks(Collection<String> guidesOfTask);
}
