package database.todoList.services.impl;

import database.todoList.dao.UserDAO;
import database.todoList.model.User;
import database.todoList.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired private UserDAO userDAO;

	public static final String THE_FIELDS_ARE_NOT_FILLED = "Какое-то из полей {GUID | LOGIN | PASSWORD |EMAIL} не заполнено.";
	public static final String NO_FOUND_USER_WITH_THE_GUID = "Не удалось найти пользователя с таким GUID-ом.";
	public static final String GUID_FIELD_IS_NOT_SET = "Поле GUID имеет значение null или не задано.";
	public static final String FILE_IS_EMPTY = "Передан пустой файл.";

	boolean validation(User user) {
		return user.getLogin() != null && user.getPassword() != null && user.geteMail() != null;
	}

	@Override
	public String signUp(User user) throws Exception {
		if (!validation(user)) throw new IllegalArgumentException(THE_FIELDS_ARE_NOT_FILLED);

		return userDAO.signUp(user);
	}

	/**
	 * @param user (login, password)
	 * @return guid
	 */
	@Override
	public String login(User user) throws Exception {
		if (!(user.getLogin() != null && user.getPassword() != null))
			throw new IllegalArgumentException(THE_FIELDS_ARE_NOT_FILLED);

		return userDAO.login(user);
	}

	@Override
	public void insertUser(User user) {
		if (!validation(user)) throw new IllegalArgumentException(THE_FIELDS_ARE_NOT_FILLED);

		userDAO.insert(user);
	}

	@Override
	public void insertUsers(Collection<User> users) {
		for (User user : users) if (!validation(user)) throw new IllegalArgumentException(THE_FIELDS_ARE_NOT_FILLED);

		userDAO.insertBatch(users);
	}

	@Override
	public User findUserByGuid(String guid) {
		if (guid == null) throw new IllegalArgumentException();

		User user = userDAO.findUserByGuid(guid);
		if (user != null) return user;
		else throw new NoSuchElementException(NO_FOUND_USER_WITH_THE_GUID);
	}

	@Override
	public Collection<User> findUsersByGuid(Collection<String> guides) {
		for (String guid : guides)
			if (guid == null) throw new IllegalArgumentException();

		Collection<User> users = userDAO.findUsersByGuid(guides);
		if (users == null) throw new NullPointerException(NO_FOUND_USER_WITH_THE_GUID);
		else return users;
	}

	@Override
	public Collection<User> findAllUsers() {
		Collection<User> users = userDAO.findAll();
		if (users != null) return users;
		else throw new NoSuchElementException(NO_FOUND_USER_WITH_THE_GUID);
	}

	@Override
	public Collection<User> findAllUsersWithoutUserSenderAndColleagues(String guidOfUserSender, Collection<String> guidesOfColleagues) {
		Collection<User> users = userDAO.findAllUsersWithoutUserSenderAndColleagues(guidOfUserSender, guidesOfColleagues);
		if (users != null) return users;
		else throw new NoSuchElementException(NO_FOUND_USER_WITH_THE_GUID);
	}

	@Override
	public void updateUser(User user) {
		if (!validation(user))
			throw new IllegalArgumentException(THE_FIELDS_ARE_NOT_FILLED);

		userDAO.update(user);
	}

	@Override
	public void deleteUser(String guid) {
		if (guid == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		userDAO.delete(guid);
	}

	@Override
	public void deleteUsers(Collection<String> guides) {
		for (String guid : guides)
			if (guid == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		userDAO.delete(guides);
	}

	@Override
	public void updatePhotoOfUser(MultipartFile file, String guidOfUser) throws Exception {
		if (file.isEmpty()) throw new Exception(FILE_IS_EMPTY);
		if (guidOfUser == null || (guidOfUser.trim().equals(""))) throw new Exception(GUID_FIELD_IS_NOT_SET);

		userDAO.updatePhotoOfUser(file, guidOfUser);
	}

	@Override
	public void updatePhotoOfUser(String fileInBASE64String, String guidOfUser) throws Exception {
		if (fileInBASE64String.isEmpty()) throw new Exception(FILE_IS_EMPTY);
		if (guidOfUser == null || (guidOfUser.trim().equals(""))) throw new Exception(GUID_FIELD_IS_NOT_SET);

		userDAO.updatePhotoOfUser(fileInBASE64String, guidOfUser);
	}

	@Override
	public String getPathPhotoOfUser(String guidOfUser) {
		return userDAO.getPathToPhotoOfUser(guidOfUser);
	}
}
