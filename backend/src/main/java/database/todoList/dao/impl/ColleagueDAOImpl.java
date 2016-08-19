package database.todoList.dao.impl;

import database.todoList.dao.ColleagueDAO;
import database.todoList.model.Colleague;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class ColleagueDAOImpl implements ColleagueDAO {
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insert(String guidOfColleague_1, String guidOfColleague_2) {
		String sql = "INSERT INTO COLLEAGUE (USER_GUID, COLLEAGUE_GUID) VALUES (?, ?);";
		jdbcTemplate.update(sql, guidOfColleague_1, guidOfColleague_2);
	}

	@Override
    public void insert(Colleague colleague) {
        String sql = "INSERT INTO COLLEAGUE (USER_GUID, COLLEAGUE_GUID) VALUES (?, ?);";
        jdbcTemplate.update(sql, colleague.getUserGuid(), colleague.getColleagueGuid());
    }

    @Override
    public void insertBatch(Collection<Colleague> colleagues) {
        for (Colleague colleague : colleagues) insert(colleague);
    }

    @Override
    public void insertBatchSQL(String sql) {
        jdbcTemplate.batchUpdate(sql);
    }

    @Override
    public Collection<String> findGuidesOfColleagues(String userGuid) {
        String sql = "SELECT COLLEAGUE_GUID FROM COLLEAGUE WHERE USER_GUID = ?;";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString("COLLEAGUE_GUID");
			}
		}, userGuid);
    }

	@Override
	public void delete(String userGuid, String guidOfColleague) {
		String sql = "DELETE FROM COLLEAGUE WHERE USER_GUID = ? AND COLLEAGUE_GUID = ?;";
		jdbcTemplate.update(sql, userGuid, guidOfColleague);
	}

	@Override
	public void delete(String userGuid, Collection<String> guidOfColleagues) {
		for (String guidOfColleague : guidOfColleagues) delete(userGuid, guidOfColleague);
	}
}