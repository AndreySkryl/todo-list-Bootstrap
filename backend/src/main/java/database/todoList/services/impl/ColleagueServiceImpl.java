package database.todoList.services.impl;

import database.todoList.dao.ColleagueDAO;
import database.todoList.services.ColleagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class ColleagueServiceImpl implements ColleagueService {
	@Autowired ColleagueDAO colleagueDAO;

	public static final String THE_OBJECT_IS_NOT_VALID = "Объект класса Colleague не прошёл валидацию.";
	public static final String INVALID_GUID = "Неверно задан GUID (возможно имеет значение null).";
	public static final String NPE = "Результатом стал NPE.";

	boolean validation(String guidOfColleague_1, String guidOfColleague_2) {
		return guidOfColleague_1 != null && guidOfColleague_2 != null &&
				!Objects.equals(guidOfColleague_1, guidOfColleague_2);
	}

	@Override
	public void insertConnectionBetweenColleagues(String guidOfColleague_1, String guidOfColleague_2) {
		if (!validation(guidOfColleague_1, guidOfColleague_2))
			throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);
		else {
			colleagueDAO.insert(guidOfColleague_1, guidOfColleague_2);
			colleagueDAO.insert(guidOfColleague_2, guidOfColleague_1);
		}
	}

	@Override
	public void insertConnectionBetweenColleagues(String guidOfColleague_1, Collection<String> listOfGuidOfColleagues) {
		for (String guidOfColleague : listOfGuidOfColleagues) {
			if (!validation(guidOfColleague_1, guidOfColleague))
				throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);
		}

		for (String guidOfColleague : listOfGuidOfColleagues) {
			colleagueDAO.insert(guidOfColleague_1, guidOfColleague);
			colleagueDAO.insert(guidOfColleague, guidOfColleague_1);
		}
	}

	@Override
	public Collection<String> findGuidesOfColleaguesByUserGuid(String userGuid) {
		if (userGuid == null) throw new IllegalArgumentException(INVALID_GUID);

		Collection<String> guidesOfColleagues = colleagueDAO.findGuidesOfColleagues(userGuid);
		if (guidesOfColleagues == null) throw new NullPointerException(NPE);
		else return guidesOfColleagues;
	}

	@Override
	public void deleteConnectionBetweenColleagues(String guidOfColleague_1, String guidOfColleague_2) {
		if (!validation(guidOfColleague_1, guidOfColleague_2))
			throw new IllegalArgumentException(INVALID_GUID);

		colleagueDAO.delete(guidOfColleague_1, guidOfColleague_2);
		colleagueDAO.delete(guidOfColleague_2, guidOfColleague_1);
	}

	@Override
	public void deleteConnectionBetweenColleagues(String guidOfColleague_1, Collection<String> listOfGuidOfColleague) {
		if (guidOfColleague_1 == null) throw new IllegalArgumentException(INVALID_GUID);
		if (listOfGuidOfColleague == null) throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);

		for (String guid : listOfGuidOfColleague)
			if (guid == null) throw new IllegalArgumentException(INVALID_GUID);

		for (String guidOfColleague : listOfGuidOfColleague)
			deleteConnectionBetweenColleagues(guidOfColleague_1, guidOfColleague);
	}

	@Override
	public void deleteConnectionsWithAllColleagues(String guidOfUser) {
		Collection<String> guidesOfColleagues = colleagueDAO.findGuidesOfColleagues(guidOfUser);
		if (guidesOfColleagues == null)
			throw new NullPointerException(NPE);

		deleteConnectionBetweenColleagues(guidOfUser, guidesOfColleagues);
	}
}
