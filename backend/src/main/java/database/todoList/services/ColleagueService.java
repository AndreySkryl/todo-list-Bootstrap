package database.todoList.services;

import java.util.Collection;

public interface ColleagueService {
	void insertConnectionBetweenColleagues(String guidOfColleague_1, String guidOfColleague_2);
	void insertConnectionBetweenColleagues(String guidOfColleague_1, Collection<String> listOfGuidOfColleagues);

	Collection<String> findGuidesOfColleaguesByUserGuid(String userGuid);

	void deleteConnectionBetweenColleagues(String guidOfColleague_1, String guidOfColleague_2);
	void deleteConnectionBetweenColleagues(String guidOfColleague_1, Collection<String> listOfGuidOfColleague);
	void deleteConnectionsWithAllColleagues(String guidOfUser);
}
