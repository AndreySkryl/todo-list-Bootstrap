package database.todoList.dao.impl;

import database.todoList.dao.UserAndListOfTasksDAO;
import database.todoList.mappers.UserAndListOfTasksRowMapper;
import database.todoList.model.UserAndListOfTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserAndListOfTasksDAOImpl implements UserAndListOfTasksDAO {
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(UserAndListOfTasks userAndListOfTasks) {
        String sql = "INSERT INTO USER__LIST_OF_TASKS (USER_GUID, LIST_OF_TASKS_GUID) VALUES (?, ?)";
        jdbcTemplate.update(sql, userAndListOfTasks.getUserGuid(), userAndListOfTasks.getListOfTasksGuid());
    }

    @Override
    public void insertBatch(Collection<UserAndListOfTasks> listOfUserAndListOfTasks) {
		for (UserAndListOfTasks userAndListOfTasks : listOfUserAndListOfTasks)
			insert(userAndListOfTasks);
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Collection<UserAndListOfTasks> findUserAndListOfTasksByListOfTasksGuid(String listOfTasksGuid) {
        String sql = "SELECT * FROM USER__LIST_OF_TASKS WHERE LIST_OF_TASKS_GUID = ?";
		return jdbcTemplate.query(sql, new UserAndListOfTasksRowMapper(), listOfTasksGuid);
    }

    @Override
    public Collection<UserAndListOfTasks> findAll() {
        String sql = "SELECT * FROM USER__LIST_OF_TASKS";
		return jdbcTemplate.query(sql, new UserAndListOfTasksRowMapper());
    }

	@Override
	public Collection<String> findListOfTasksGuidByUserGuid(String guidOfUser) {
		String sql = "SELECT LIST_OF_TASKS_GUID FROM USER__LIST_OF_TASKS WHERE USER_GUID = ?;";
		return jdbcTemplate.queryForList(sql, new Object[]{guidOfUser}, String.class);
	}

	@Override
	public int findCountOfUserAndListOfTasks() {
		String sql = "SELECT COUNT(*) FROM USER__LIST_OF_TASKS";
		Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	@Override
	public void updateGuidOfList(String oldGuidOfListOfTasks, String newGuidOfListOfTasks) {
		String sql = "UPDATE USER__LIST_OF_TASKS SET LIST_OF_TASKS_GUID = ? WHERE LIST_OF_TASKS_GUID = ?;";
		jdbcTemplate.update(sql, newGuidOfListOfTasks, oldGuidOfListOfTasks);
	}

	@Override
	public void delete(String guidOfListOfTasks, String guidOfUser) {
		String sql = "DELETE FROM USER__LIST_OF_TASKS WHERE USER_GUID = ? AND LIST_OF_TASKS_GUID = ?;";
		jdbcTemplate.update(sql, guidOfUser, guidOfListOfTasks);
	}

	@Override
	public void deleteByGuidOfListOfTasks(String guidOfListOfTasks) {
		String sql = "DELETE FROM USER__LIST_OF_TASKS WHERE LIST_OF_TASKS_GUID = ?;";
		jdbcTemplate.update(sql, guidOfListOfTasks);
	}
}