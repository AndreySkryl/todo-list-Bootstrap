package database.todoList.dao;

import database.todoList.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

public interface UserDAO {
    String signUp(User user) throws Exception;
    String login(User user) throws Exception;

    void insert(User user);
    void insertBatch(Collection<User> users);
    void insertBatchSQL(String sql);

    User findUserByGuid(String guid);
    Collection<User> findUsersByGuid(Collection<String> guides);
    Collection<User> findAll();
    Collection<User> findAllUsersWithoutUserSenderAndColleagues(String guidOfUserSender);

    void update(User user);

    void delete(String guid);
	void delete(Collection<String> guides);

    void updatePhotoOfUser(MultipartFile file, String guidOfUser) throws IOException;
    void updatePhotoOfUser(String fileInBASE64String, String guidOfUser) throws IOException;

    String getPathToPhotoOfUser(String guidOfUser);
}