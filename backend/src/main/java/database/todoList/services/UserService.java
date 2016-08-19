package database.todoList.services;

import database.todoList.model.User;

import java.util.Collection;

public interface UserService {
	String signUp(User user) throws Exception;
	String login(User user) throws Exception;

	void insertUser(User user);
	void insertUsers(Collection<User> users);

	User findUserByGuid(String guid);
	Collection<User> findUsersByGuid(Collection<String> guides);
	Collection<User> findAllUsers();
	Collection<User> findAllUsersWithoutUserSenderAndColleagues(String guidOfUserSender, Collection<String> guidesOfColleagues);

	void updateUser(User user);

	void deleteUser(String guid);
	void deleteUsers(Collection<String> guides);
}
