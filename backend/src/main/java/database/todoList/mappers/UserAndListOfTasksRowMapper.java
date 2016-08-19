package database.todoList.mappers;

import database.todoList.model.UserAndListOfTasks;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAndListOfTasksRowMapper implements RowMapper<UserAndListOfTasks> {
    @Override
    public UserAndListOfTasks mapRow(ResultSet resultSet, int i) throws SQLException {
        UserAndListOfTasks userAndListOfTasks = new UserAndListOfTasks();
        userAndListOfTasks.setUserGuid(resultSet.getString("USER_GUID"));
        userAndListOfTasks.setListOfTasksGuid(resultSet.getString("LIST_OF_TASKS_GUID"));
        return userAndListOfTasks;
    }
}
