package database.todoList.dao.impl;

import database.todoList.dao.UserDAO;
import database.todoList.mappers.UserRowMapper;
import database.todoList.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        String sql = "INSERT INTO USER (LOGIN, LASTNAME, FIRSTNAME, PASSWORD, EMAIL) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, user.getLogin(), user.getLastName(), user.getFirstName(),
				user.getPassword(), user.geteMail());
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
	public Collection<User> findAllUsersWithoutUserSenderAndColleagues(String guidOfUserSender, Collection<String> guidesOfColleagues) {
		List<String> guidesOfUserSenderAndColleagues = new ArrayList<>();
		guidesOfUserSenderAndColleagues.add(guidOfUserSender);
		guidesOfUserSenderAndColleagues.addAll(guidesOfColleagues);

		StringBuilder stringBuilder = new StringBuilder();
		for (int id = 0; id < guidesOfUserSenderAndColleagues.size(); id++) {
			stringBuilder.append("'").append(guidesOfUserSenderAndColleagues.get(id)).append("'");
			if (id != guidesOfUserSenderAndColleagues.size() - 1) {
				stringBuilder.append(",");
			}
		}

		String sql = "SELECT * FROM user WHERE GUID NOT IN ( SELECT GUID FROM user WHERE GUID IN (" + stringBuilder.toString() + ") );";
		return jdbcTemplate.query(sql, new UserRowMapper());
		//String sql = "SELECT * FROM user WHERE GUID NOT IN ( SELECT GUID FROM user WHERE GUID IN (?) );";
		//return jdbcTemplate.query(sql, new UserRowMapper(), guidesOfUserSenderAndColleagues);
	}

    @Override
    public void update(User user) {
		String password = DigestUtils.md5Hex(user.getPassword());
		String sql = "UPDATE USER SET LOGIN = ?, LASTNAME = ?, FIRSTNAME = ?, PASSWORD = ?, EMAIL = ? WHERE GUID = ?;";
		jdbcTemplate.update(sql, user.getLogin(), user.getLastName(), user.getFirstName(), password,
				user.geteMail(), user.getGuid());
    }

    @Override
    public void delete(String guid) {
        String sql = "DELETE FROM USER WHERE GUID = ?;";
        jdbcTemplate.update(sql, guid);
    }

	@Override
	public void delete(Collection<String> guides) {
		for (String guid : guides) delete(guid);
	}
}