package database.todoList.mappers;

import database.todoList.model.Colleague;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColleagueRowMapper implements RowMapper<Colleague> {
    @Override
    public Colleague mapRow(ResultSet resultSet, int i) throws SQLException {
        Colleague colleague = new Colleague();
        colleague.setUserGuid((resultSet.getString("USER_GUID")));
        colleague.setColleagueGuid(resultSet.getString("COLLEAGUE_GUID"));
        colleague.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));
        colleague.setUpdateTime(resultSet.getTimestamp("UPDATE_TIME"));
        return colleague;
    }
}