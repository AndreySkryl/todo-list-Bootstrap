package database.todoList.dao;

import database.todoList.model.ListOfTasks;
import database.todoList.model.User;

import java.util.Collection;

public interface ListOfTasksDAO {
	String insert(ListOfTasks listOfTasks);

	Collection<String> insertBatch(Collection<ListOfTasks> listOfTasks);

	void insertBatchSQL(String sql);

	void fillingData(String guidOfListOfTasks, String guidOfTemplateListOfTasks);

	ListOfTasks findListOfTasksByGuid(String guidOfListOfTasks);

	Collection<ListOfTasks> findAll();

	int findTotalListOfTasks();

	String findGuidOfOwnerOfListOfTasks(String guidOfListOfTasks);

	void update(ListOfTasks listOfTasks);

	void delete(String guidOfListOfTasks);

	Collection<User> getAllSubscribersForListOfTask(String guidOfListOfTask);

	Collection<User> getAllUnsignedForListOfTask(String guidOfListOfTask, String guidOfUser);
}
