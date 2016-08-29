package database.todoList.services;

import database.todoList.exceptions.UserIsNotOwnerOfListOfTasksException;
import database.todoList.model.ListOfTasks;
import database.todoList.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ListOfTasksService {
	void insertListOfTasks(ListOfTasks listOfTasks);
	void insertListOfTasksBasedOnTheTemplate(ListOfTasks listOfTasks, String guidOfTemplateListOfTasks);
	void insertListsOfTasks(Collection<ListOfTasks> listOfTasksCollection);

	ListOfTasks findListOfTasksByGuid(String guidOfListOfTasks);
	Collection<ListOfTasks> findAllListOfTasksOfUser(String guidOfUser);

	void updateListOfTasks(String guidOfUser, ListOfTasks listOfTasks) throws UserIsNotOwnerOfListOfTasksException;

	void deleteListOfTasks(String guidOfListOfTasks, String guidOfUser) throws UserIsNotOwnerOfListOfTasksException;

	void subscribeUserToListOfTasks(String guidOfListOfTasks, String guidOfUser);
	void unsubscribeUserToListOfTasks(String guidOfListOfTasks, String guidOfUser);
	Collection<User> getAllSubscribersForListOfTask(String guidOfListOfTask);
	Collection<User> getAllUnsignedForListOfTask(String guidOfListOfTask, String guidOfUser);

	Double getPercentOfPlannedTasks(String guidOfListOfTask);
}
