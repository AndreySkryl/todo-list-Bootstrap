package database.todoList.dao.impl;

import database.todoList.dao.UserDAO;
import database.todoList.mappers.UserRowMapper;
import database.todoList.model.User;
import database.todoList.utils.UtilForManageResources;
import database.todoList.utils.UtilForWorkWithFileAndDirectories;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

	@Override
	public String signUp(User user) throws Exception {
		List<User> usersFromDB;
		String sql = "SELECT * FROM USER WHERE LOGIN = ?;";
		usersFromDB = jdbcTemplate.query(sql, new UserRowMapper(), user.getLogin());
		if (!usersFromDB.isEmpty()) throw new Exception("Пользователь с логином уже существует.");

		sql = "SELECT * FROM USER WHERE EMAIL = ?;";
		usersFromDB = jdbcTemplate.query(sql, new UserRowMapper(), user.geteMail());
		if (!usersFromDB.isEmpty()) throw new Exception("Пользователь с такой эл. почтой уже существует.");

		insert(user);

		return "";
	}

	@Override
	public String login(User user) throws Exception {
		String sql = "SELECT * FROM USER WHERE LOGIN = ? OR EMAIL = ?;";
		List<User> usersFromDB = jdbcTemplate.query(sql, new UserRowMapper(), user.getLogin(), user.getLogin());

		if (usersFromDB.size() != 0) {
			User userFromDB = usersFromDB.get(0);

			String password = DigestUtils.md5Hex(user.getPassword());
			if (password.equals(userFromDB.getPassword())) return userFromDB.getGuid();
			else throw new Exception("Указан неверный пароль.");
		} else throw new Exception("Пользователя с таким логином (e-mail) не удалось найти.");
	}

    @Override
    public void insert(User user) {
		String guidOfUser = UUID.randomUUID().toString();
		try {
			while (true) {
				String sqlForCountOfUserWithTheGuid = "SELECT COUNT(*) FROM USER WHERE GUID = ?;";
				Integer count = 0;
				try {
					count = jdbcTemplate.queryForObject(sqlForCountOfUserWithTheGuid, new Object[]{guidOfUser}, Integer.class);
					if (count == null || (count != null && count == 0)) break;
				} catch (Throwable ignored) {
					System.err.println(count.toString());
				}

				guidOfUser = UUID.randomUUID().toString();
			}

			// создание директории для пользователя
			File pathForDirs = new File(UtilForManageResources.PATH_TO_RESOURCES_OF_TABLE_USER + guidOfUser);
			pathForDirs.mkdirs();
			String pathToPhotoOfUser = "";
			try {
				File fileForIn = new File(UtilForManageResources.PATH_TO_DEFAULT_PHOTO_OF_USER);
				pathToPhotoOfUser = UtilForManageResources.PATH_TO_RESOURCES_OF_TABLE_USER + guidOfUser + "/" + fileForIn.getName();
				File fileForOut = new File(pathToPhotoOfUser);
				// копирование default-ого изображения для аватара пользователя
				UtilForWorkWithFileAndDirectories.copyFileUsingStream(fileForIn, fileForOut);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String sql = "INSERT INTO USER (GUID, LOGIN, LASTNAME, FIRSTNAME, PASSWORD, EMAIL, PATH_TO_PHOTO_OF_USER)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?);";
			jdbcTemplate.update(sql, guidOfUser, user.getLogin(), user.getLastName(), user.getFirstName(),
					user.getPassword(), user.geteMail(), pathToPhotoOfUser);
		} catch (Throwable throwable) {
			throw throwable;
		}
	}

    @Override
    public void insertBatch(Collection<User> users) {
        for (User user : users) insert(user);
    }

    @Override
    public void insertBatchSQL(final String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public User findUserByGuid(String guid) {
        String sql = "SELECT * FROM USER WHERE GUID = ?;";
		return jdbcTemplate.queryForObject(sql, new UserRowMapper(), guid);
    }

	@Override
	public Collection<User> findUsersByGuid(Collection<String> guides) {
		Collection<User> listOfUser = new ArrayList<>();
		for (String guid : guides) {
			User user = findUserByGuid(guid);
			listOfUser.add(user);
		}
		return listOfUser;
	}

	@Override
    public Collection<User> findAll() {
        String sql = "SELECT * FROM USER;";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

	@Override
	public Collection<User> findAllUsersWithoutUserSenderAndColleagues(String guidOfUserSender) {
		String sql =
				"SELECT u0.* " +
				"FROM user u0 " +
				"WHERE NOT EXISTS( " +
					"SELECT colleague.COLLEAGUE_GUID " +
					"FROM colleague " +
					"WHERE colleague.USER_GUID = '2bcbfd86-14ec-4b4c-9f12-e1a24923b141' " +
							"AND (u0.GUID = colleague.COLLEAGUE_GUID OR u0.GUID = colleague.USER_GUID) " +
				"); ";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

    @Override
    public void update(User user) {
		User userFromDB = findUserByGuid(user.getGuid());
		String password;
		if (user.getPassword().equals(userFromDB.getPassword())) password = userFromDB.getPassword();
		else password = DigestUtils.md5Hex(user.getPassword());

		String sql = "UPDATE USER SET LOGIN = ?, LASTNAME = ?, FIRSTNAME = ?, PASSWORD = ?, EMAIL = ? WHERE GUID = ?;";
		jdbcTemplate.update(sql, user.getLogin(), user.getLastName(), user.getFirstName(), password,
				user.geteMail(), user.getGuid());
    }

    @Override
    public void delete(String guid) {
		String sql = "DELETE FROM USER WHERE GUID = ?;";
		jdbcTemplate.update(sql, guid);

		UtilForWorkWithFileAndDirectories.deleteDirectory(new File(UtilForManageResources.PATH_TO_RESOURCES_OF_TABLE_USER + guid));
    }

	@Override
	public void delete(Collection<String> guides) {
		for (String guid : guides) delete(guid);
	}

	@Override
	public void updatePhotoOfUser(MultipartFile file, String guidOfUser) throws IOException {
		String pathToOldPhotoOfUser = getPathToPhotoOfUser(guidOfUser);
		UtilForWorkWithFileAndDirectories.deleteFile(pathToOldPhotoOfUser);

		byte[] bytesOfImage = file.getBytes();
		String fileExtension = UtilForWorkWithFileAndDirectories.getFileExtension(file.getOriginalFilename());
		String pathToNewPhotoOfUser = UtilForManageResources.PATH_TO_RESOURCES_OF_TABLE_USER + guidOfUser + "/" +
				UtilForManageResources.DEFAULT_NAME_OF_FILE_OF_PHOTO_OF_USER + "." + fileExtension;
		UtilForWorkWithFileAndDirectories.saveBytesToFile(bytesOfImage, pathToNewPhotoOfUser);

		String sql = "UPDATE USER SET PATH_TO_PHOTO_OF_USER = ? WHERE GUID = ?";
		jdbcTemplate.update(sql, pathToNewPhotoOfUser, guidOfUser);
	}

	@Override
	public void updatePhotoOfUser(String fileInBASE64String, String guidOfUser) throws IOException {
		// проверка данных на BASE64 String
		Pattern pattern = Pattern.compile("data:image/([a-z]+);base64,([a-zA-Z0-9+/=]+)");
		Matcher matcher = pattern.matcher(fileInBASE64String);

		if (!matcher.matches()) throw new IllegalArgumentException("В неверном формате пришли данные BASE64 String.");

		String pathToOldPhotoOfUser = getPathToPhotoOfUser(guidOfUser);
		UtilForWorkWithFileAndDirectories.deleteFile(pathToOldPhotoOfUser);

		String fileExtension = matcher.group(1);
		String imageInBASE64String = matcher.group(2);
		byte[] bytesOfImage = Base64.getDecoder().decode(imageInBASE64String);
		String pathToNewPhotoOfUser = UtilForManageResources.PATH_TO_RESOURCES_OF_TABLE_USER + guidOfUser + "/" +
				UtilForManageResources.DEFAULT_NAME_OF_FILE_OF_PHOTO_OF_USER + "." + fileExtension;
		UtilForWorkWithFileAndDirectories.saveBytesToFile(bytesOfImage, pathToNewPhotoOfUser);

		String sql = "UPDATE USER SET PATH_TO_PHOTO_OF_USER = ? WHERE GUID = ?";
		jdbcTemplate.update(sql, pathToNewPhotoOfUser, guidOfUser);
	}

	@Override
	public String getPathToPhotoOfUser(String guidOfUser) {
		String sql = "SELECT PATH_TO_PHOTO_OF_USER FROM USER WHERE GUID = ?;";

		return jdbcTemplate.queryForObject(sql, new Object[]{guidOfUser}, String.class);
	}
}