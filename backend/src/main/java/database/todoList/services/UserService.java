package database.todoList.services;

import database.todoList.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface UserService {

	String signUp(User user) throws Exception;
	String login(User user) throws Exception;

	void insertUser(User user);
	void insertUsers(Collection<User> users);

	User findUserByGuid(String guid);
	Collection<User> findUsersByGuid(Collection<String> guides);
	Collection<User> findAllUsers();
	Collection<User> findAllUsersWithoutUserSenderAndColleagues(String guidOfUserSender);

	void updateUser(User user);

	void deleteUser(String guid);
	void deleteUsers(Collection<String> guides);

	void updatePhotoOfUser(MultipartFile file, String guidOfUser) throws Exception;
	void updatePhotoOfUser(String fileInBASE64String, String guidOfUser) throws Exception;
	String getPathPhotoOfUser(String guidOfUser);
}
