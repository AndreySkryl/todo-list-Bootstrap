package database.todoList.services.impl;

import database.todoList.dao.TaskDAO;
import database.todoList.model.Task;
import database.todoList.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired private TaskDAO taskDAO;

	public static final String INVALID_GUID = "Неверно задан GUID (возможно имеет значение null).";
	public static final String THE_OBJECT_IS_NOT_VALID = "Объект класса Task не прошёл валидацию.";
	public static final String NPE = "Результатом стал NPE.";

	boolean validation(Task task) {
		return task.getListOfTasksGuid() != null && task.getStatus() != null &&
				task.getDescription() != null && !task.getDescription().isEmpty();
	}

	@Override
	public void insertTask(Task task) {
		if (!validation(task)) throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);
		taskDAO.insert(task);
	}

	@Override
	public void insertTasks(Collection<Task> tasks) {
		for (Task task : tasks) if (!validation(task)) throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);
		for (Task task : tasks) taskDAO.insert(task);
	}

	@Override
	public Task findTaskByGuid(String guidOfTask) {
		if (guidOfTask == null) throw new IllegalArgumentException(INVALID_GUID);

		Task task = taskDAO.findTaskByGuid(guidOfTask);
		if (task == null) throw new NullPointerException(NPE);

		return task;
	}

	@Override
	public Collection<Task> findAllTasksOfListOfTasks(String guidOfListOfTasks) {
		if (guidOfListOfTasks == null) throw new IllegalArgumentException(INVALID_GUID);

		Collection<Task> allTasksOfListOfTasks = taskDAO.findAllTasksOfListOfTasks(guidOfListOfTasks);

		if (allTasksOfListOfTasks == null) throw new NullPointerException(NPE);

		return allTasksOfListOfTasks;
	}

	@Override
	public int findCountOfTasks() {
		return taskDAO.findTotalTasks();
	}

	@Override
	public void updateTask(Task task) {
		if (!validation(task)) throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);

		taskDAO.update(task);
	}

	@Override
	public void deleteTask(String guidOfTask) {
		if (guidOfTask == null) throw new IllegalArgumentException(INVALID_GUID);

		taskDAO.delete(guidOfTask);
	}

	@Override
	public void deleteTasks(Collection<String> guidesOfTask) {
		for (String guidOfTask : guidesOfTask) taskDAO.delete(guidOfTask);
	}
}
