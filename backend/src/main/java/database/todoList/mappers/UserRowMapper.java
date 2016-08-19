package database.todoList.mappers;

import database.todoList.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setGuid(resultSet.getString("GUID"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setLastName(resultSet.getString("LASTNAME"));
        user.setFirstName(resultSet.getString("FIRSTNAME"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.seteMail(resultSet.getString("EMAIL"));
        user.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));
        user.setUpdateTime(resultSet.getTimestamp("UPDATE_TIME"));
        return user;
    }
}
