package database.todoList.mappers;

import database.todoList.model.Status;
import database.todoList.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setListOfTasksGuid(resultSet.getString("LIST_OF_TASKS_GUID"));
        task.setGuid(resultSet.getString("GUID"));
        String status = resultSet.getString("STATUS");
        task.setStatus(Status.fromString(status));
        task.setDescription(resultSet.getString("DESCRIPTION"));
        task.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));
        task.setUpdateTime(resultSet.getTimestamp("UPDATE_TIME"));
        return task;
    }
}
