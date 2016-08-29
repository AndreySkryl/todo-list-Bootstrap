package database.todoList.dao.impl;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.mappers.ListOfTasksRowMapper;
import database.todoList.mappers.UserRowMapper;
import database.todoList.model.ListOfTasks;
import database.todoList.model.Task;
import database.todoList.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public class ListOfTasksDAOImpl implements ListOfTasksDAO {
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate;

	@Autowired(required = false)
	private TaskDAOImpl taskDAO;

	@Override
	public String insert(ListOfTasks listOfTasks) {
		String guidOfListOfTasks = UUID.randomUUID().toString();
		if (listOfTasks.getGuid() != null) guidOfListOfTasks = listOfTasks.getGuid();

		String sql = "INSERT INTO LIST_OF_TASKS (GUID, USER_GUID, FAVOURITES, NAME, DESCRIPTION) VALUES (?, ?, ?, ?, ?);";
		jdbcTemplate.update(sql, guidOfListOfTasks, listOfTasks.getUserGuid(),
				listOfTasks.getFavourites(), listOfTasks.getName(), listOfTasks.getDescription());

		return guidOfListOfTasks;
	}

	@Override
	public Collection<String> insertBatch(Collection<ListOfTasks> listOfTasks) {
		List<String> listOfGuidOfListOfTasks = new ArrayList<>();
		for (ListOfTasks listOfTask : listOfTasks) {
			String guid = insert(listOfTask);
			listOfGuidOfListOfTasks.add(guid);
		}
		return listOfGuidOfListOfTasks;
	}

	@Override
	public void insertBatchSQL(String sql) {
		jdbcTemplate.batchUpdate(sql);
	}

	@Override
	public void fillingData(String guidOfListOfTasks, String guidOfTemplateListOfTasks) {
		// вытаскиваем все имеющиеся задачи у списка-шаблона
		Collection<Task> tasksOfTemplateListOfTasks = taskDAO.findAllTasksOfListOfTasks(guidOfTemplateListOfTasks);

		// перебиваем guid-ы списка задач
		for (Task taskOfTemplateListOfTasks : tasksOfTemplateListOfTasks) {
			taskOfTemplateListOfTasks.setListOfTasksGuid(guidOfListOfTasks);
		}

		// записываем все задачи полученные из списка-шаблона в создаваемый список задач
		taskDAO.insertBatch(tasksOfTemplateListOfTasks);
	}

	@Override
	public ListOfTasks findListOfTasksByGuid(String guidOfListOfTasks) {
		String sql = "SELECT * FROM LIST_OF_TASKS WHERE GUID = ?;";
		return jdbcTemplate.queryForObject(sql, new ListOfTasksRowMapper(), guidOfListOfTasks);
	}

	@Override
	public Collection<ListOfTasks> findAll() {
		String sql = "SELECT * FROM LIST_OF_TASKS;";
		return jdbcTemplate.query(sql, new ListOfTasksRowMapper());
	}

	@Override
	public int findTotalListOfTasks() {
		String sql = "SELECT COUNT(*) FROM LIST_OF_TASKS;";
		Number number = jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	@Override
	public String findGuidOfOwnerOfListOfTasks(String guidOfListOfTasks) {
		String sql = "SELECT USER_GUID FROM LIST_OF_TASKS WHERE GUID = ?;";
		return jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return resultSet.getString("USER_GUID" );
			}
		}, guidOfListOfTasks);
	}

	@Override
	public void update(ListOfTasks listOfTasks) {
		String sql = "UPDATE LIST_OF_TASKS SET USER_GUID = ?, FAVOURITES = ?, NAME = ?, DESCRIPTION = ? WHERE GUID = ?;";
		jdbcTemplate.update(sql, listOfTasks.getUserGuid(), listOfTasks.getFavourites(),
				listOfTasks.getName(), listOfTasks.getDescription(), listOfTasks.getGuid());
	}

	@Override
	public void delete(String guidOfListOfTasks) {
		String sql = "DELETE FROM LIST_OF_TASKS WHERE GUID = ?;";
		jdbcTemplate.update(sql, guidOfListOfTasks);
	}

	@Override
	public Collection<User> getAllSubscribersForListOfTask(String guidOfListOfTask) {
		String sql =
				"SELECT user.* " +
				"FROM user " +
				"WHERE EXISTS( " +
					"SELECT user__list_of_tasks.USER_GUID " +
					"FROM user__list_of_tasks " +
					"WHERE user__list_of_tasks.LIST_OF_TASKS_GUID = ? " +
							"AND user__list_of_tasks.USER_GUID = user.GUID" +
				");";
		return jdbcTemplate.query(sql, new UserRowMapper(), guidOfListOfTask);
	}

	@Override
	public Collection<User> getAllUnsignedForListOfTask(String guidOfListOfTask, String guidOfUser) {
		String sql =
				"SELECT * " +
				"FROM ( " +
					"SELECT u0.* " +
					"FROM user u0 " +
					"WHERE EXISTS( " +
						"SELECT colleague.COLLEAGUE_GUID " +
						"FROM colleague " +
						"WHERE colleague.USER_GUID = ? " +
							"AND u0.GUID = colleague.COLLEAGUE_GUID " +
					") " +
				") AS allColleaguesAsUsers " +
				"WHERE NOT EXISTS( " +
					"SELECT u_LT.USER_GUID " +
					"FROM user__list_of_tasks u_LT " +
					"WHERE u_LT.LIST_OF_TASKS_GUID = ? " +
					"AND allColleaguesAsUsers.GUID = u_LT.USER_GUID " +
				");";
		return jdbcTemplate.query(sql, new UserRowMapper(), guidOfUser, guidOfListOfTask);
	}

	@Override
	public Double getPercentOfPlannedTasks(String guidOfListOfTask) {
		String sql =
				"SELECT " +
					"(SELECT COUNT(*) FROM TASK WHERE TASK.LIST_OF_TASKS_GUID = ? " +
													"AND TASK.STATUS = 'PROCESS') " +
					"/ " +
					"(SELECT COUNT(*) FROM TASK WHERE TASK.LIST_OF_TASKS_GUID = ?);";

		Double result = jdbcTemplate.queryForObject(sql, Double.class, guidOfListOfTask);

		if (result == null) result = 0d;
		return result;
	}

}