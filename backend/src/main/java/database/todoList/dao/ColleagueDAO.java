package database.todoList.dao;

import database.todoList.model.Colleague;

import java.util.Collection;

public interface ColleagueDAO {
	void insert(String guidOfColleague_1, String guidOfColleague_2);
    void insert(Colleague colleague);
    void insertBatch(Collection<Colleague> colleagues);
    void insertBatchSQL(String sql);

    Collection<String> findGuidesOfColleagues(String userGuid);

	void delete(String userGuid, String guidOfColleague);
	void delete(String userGuid, Collection<String> guidOfColleagues);
}
