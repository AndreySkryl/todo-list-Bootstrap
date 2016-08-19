package database.todoList.mappers;

import database.todoList.model.ListOfTasks;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListOfTasksRowMapper implements RowMapper<ListOfTasks> {
    @Override
    public ListOfTasks mapRow(ResultSet resultSet, int i) throws SQLException {
        ListOfTasks listOfTasks = new ListOfTasks();
        listOfTasks.setGuid(resultSet.getString("GUID"));
        listOfTasks.setUserGuid((resultSet.getString("USER_GUID")));
        listOfTasks.setFavourites(resultSet.getInt("FAVOURITES"));
        listOfTasks.setName(resultSet.getString("NAME"));
        listOfTasks.setDescription(resultSet.getString("DESCRIPTION"));
        listOfTasks.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));
        listOfTasks.setUpdateTime(resultSet.getTimestamp("UPDATE_TIME"));
        return listOfTasks;
    }
}
