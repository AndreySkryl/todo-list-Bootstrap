package database.todoList.dao;

import database.todoList.model.UserAndListOfTasks;

import java.util.Collection;

public interface UserAndListOfTasksDAO {
    void insert(UserAndListOfTasks userAndListOfTasks);
    void insertBatch(Collection<UserAndListOfTasks> listOfUserAndListOfTasks);
    void insertBatchSQL(String sql);

    Collection<UserAndListOfTasks> findUserAndListOfTasksByListOfTasksGuid(String listOfTasksGuid);
    Collection<UserAndListOfTasks> findAll();
    Collection<String> findListOfTasksGuidByUserGuid(String guidOfUser);
    int findCountOfUserAndListOfTasks();

    void updateGuidOfList(String oldGuidOfListOfTasks, String newGuidOfListOfTasks);

	void delete(String guidOfListOfTasks, String guidOfUser);
	void deleteByGuidOfListOfTasks(String guidOfListOfTasks);
}
