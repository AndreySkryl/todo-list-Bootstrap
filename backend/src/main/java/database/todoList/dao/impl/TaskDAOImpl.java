package database.todoList.dao.impl;

import database.todoList.dao.TaskDAO;
import database.todoList.mappers.TaskRowMapper;
import database.todoList.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TaskDAOImpl implements TaskDAO {
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Task task) {
        String sql = "INSERT INTO TASK (LIST_OF_TASKS_GUID, STATUS, DESCRIPTION) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, task.getListOfTasksGuid(), task.getStatus().toString(), task.getDescription());
    }

    @Override
    public void insertBatch(Collection<Task> tasks) {
        for (Task task : tasks) insert(task);
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Task findTaskByGuid(String guid) {
        String sql = "SELECT * FROM TASK WHERE GUID = ?";
		return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), guid);
    }

    @Override
    public Collection<Task> findAll() {
        String sql = "SELECT * FROM TASK";
		return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public Collection<Task> findAllTasksOfListOfTasks(String guidOfListOfTasks) {
        String sql = "SELECT * FROM TASK WHERE TASK.LIST_OF_TASKS_GUID = ?;";
        return jdbcTemplate.query(sql, new TaskRowMapper(), guidOfListOfTasks);
    }

    @Override
    public int findTotalTasks() {
        String sql = "SELECT COUNT(*) FROM TASK";
        Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    @Override
    public void update(Task task) {
		String sql = "UPDATE TASK SET LIST_OF_TASKS_GUID = ?, STATUS = ?, DESCRIPTION = ? WHERE GUID = ?;";
		jdbcTemplate.update(sql, task.getListOfTasksGuid(), task.getStatus().getValue(), task.getDescription(), task.getGuid());
	}

	@Override
	public void delete(String guidOfTask) {
		String sql = "DELETE FROM TASK WHERE GUID = ?;";
		jdbcTemplate.update(sql, guidOfTask);
	}

	@Override
	public void deleteByGuidOfListOfTasks(String guidOfListOfTask) {
		String sql = "DELETE FROM TASK WHERE LIST_OF_TASKS_GUID = ?;";
		jdbcTemplate.update(sql, guidOfListOfTask);
	}
}