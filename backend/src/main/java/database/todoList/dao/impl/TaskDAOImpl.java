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
		String sqlForMaxDisplayPositionOfTask = "SELECT MAX(DISPLAY_POSITION) FROM task;";
		Integer number = jdbcTemplate.queryForObject(sqlForMaxDisplayPositionOfTask, new Object[]{}, Integer.class);
		if (number == null) number = 0;
		else number = number + 1;

		String sql = "INSERT INTO TASK (LIST_OF_TASKS_GUID, STATUS, DESCRIPTION, DISPLAY_POSITION) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getListOfTasksGuid(), task.getStatus().toString(), task.getDescription(), number.intValue());

		changeStatusOnIncrement(task);
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
        String sql = "SELECT * FROM TASK ORDER BY DISPLAY_POSITION ASC;";
		return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public Collection<Task> findAllTasksOfListOfTasks(String guidOfListOfTasks) {
        String sql = "SELECT * FROM TASK WHERE TASK.LIST_OF_TASKS_GUID = ? ORDER BY DISPLAY_POSITION ASC;";
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
		Task taskFromDB = findTaskByGuid(task.getGuid());

		String sql = "UPDATE TASK SET LIST_OF_TASKS_GUID = ?, STATUS = ?, DESCRIPTION = ?, DISPLAY_POSITION = ? WHERE GUID = ?;";
		jdbcTemplate.update(sql, task.getListOfTasksGuid(), task.getStatus().getValue(), task.getDescription(), task.getDisplayPosition(), task.getGuid());

		changeStatusOnDecrement(taskFromDB);
		changeStatusOnIncrement(task);
	}

	public void update(Collection<Task> tasks) {
		for (Task task : tasks) { update(task); }
	}

	@Override
	public void delete(String guidOfTask) {
		Task task = findTaskByGuid(guidOfTask);

		String sql = "DELETE FROM TASK WHERE GUID = ?;";
		jdbcTemplate.update(sql, guidOfTask);

		changeStatusOnDecrement(task);
	}

	@Override
	public void deleteByGuidOfListOfTasks(String guidOfListOfTask) {
		String sql = "DELETE FROM TASK WHERE LIST_OF_TASKS_GUID = ?;";
		jdbcTemplate.update(sql, guidOfListOfTask);
	}

	public void changeStatusOnIncrement(Task task) {
		switch (task.getStatus()) {
			case PLAN:
				String sqlForPlan =
						"UPDATE LIST_OF_TASKS " +
						"SET COUNT_OF_PLAN_TASKS = COUNT_OF_PLAN_TASKS + 1 " +
						"WHERE LIST_OF_TASKS.GUID = ?;";
				jdbcTemplate.update(sqlForPlan, task.getListOfTasksGuid());
				break;

			case PROCESS:
				String sqlForProcess =
						"UPDATE LIST_OF_TASKS " +
						"SET COUNT_OF_PROCESS_TASKS = COUNT_OF_PROCESS_TASKS + 1 " +
						"WHERE LIST_OF_TASKS.GUID = ?;";
				jdbcTemplate.update(sqlForProcess, task.getListOfTasksGuid());
				break;

			case DONE:
				String sqlForDone =
						"UPDATE LIST_OF_TASKS " +
						"SET COUNT_OF_DONE_TASKS = COUNT_OF_DONE_TASKS + 1 " +
						"WHERE LIST_OF_TASKS.GUID = ?;";
				jdbcTemplate.update(sqlForDone, task.getListOfTasksGuid());
				break;
		}
	}
	public void changeStatusOnDecrement(Task task) {
		switch (task.getStatus()) {
			case PLAN:
				String sqlForPlan =
						"UPDATE LIST_OF_TASKS " +
						"SET COUNT_OF_PLAN_TASKS = COUNT_OF_PLAN_TASKS - 1 " +
						"WHERE LIST_OF_TASKS.GUID = ?;";
				jdbcTemplate.update(sqlForPlan, task.getListOfTasksGuid());
				break;

			case PROCESS:
				String sqlForProcess =
						"UPDATE LIST_OF_TASKS " +
						"SET COUNT_OF_PROCESS_TASKS = COUNT_OF_PROCESS_TASKS - 1 " +
						"WHERE LIST_OF_TASKS.GUID = ?;";
				jdbcTemplate.update(sqlForProcess, task.getListOfTasksGuid());
				break;

			case DONE:
				String sqlForDone =
						"UPDATE LIST_OF_TASKS " +
						"SET COUNT_OF_DONE_TASKS = COUNT_OF_DONE_TASKS - 1 " +
						"WHERE LIST_OF_TASKS.GUID = ?;";
				jdbcTemplate.update(sqlForDone, task.getListOfTasksGuid());
				break;
		}
	}
}