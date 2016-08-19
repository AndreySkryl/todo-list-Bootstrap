package database.todoList.services.impl;

import database.todoList.dao.ListOfTasksDAO;
import database.todoList.dao.TaskDAO;
import database.todoList.dao.UserAndListOfTasksDAO;
import database.todoList.exceptions.UserIsNotOwnerOfListOfTasksException;
import database.todoList.model.ListOfTasks;
import database.todoList.model.User;
import database.todoList.model.UserAndListOfTasks;
import database.todoList.services.ListOfTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class ListOfTasksServiceImpl implements ListOfTasksService {
	@Autowired private ListOfTasksDAO listOfTasksDAO;
	@Autowired private UserAndListOfTasksDAO userAndListOfTasksDAO;
	@Autowired private TaskDAO taskDAO;

	public static final String GUID_FIELD_IS_NOT_SET = "Поле GUID имеет значение null.";
	public static final String THE_OBJECT_IS_NOT_VALID = "Объект класса ListOfTasks не прошёл валидацию.";
	public static final String NPE = "Результатом стал NPE.";
	public static final String ADMIN_OF_LIST_CANNOT_UNSUBSCRIBE_FROM_LIST =
			"Данный пользователь не может отписаться от данного списка, т.к. является его админом.";

	boolean validation(ListOfTasks listOfTasks) {
		return listOfTasks.getUserGuid() != null && listOfTasks.getFavourites() != null && listOfTasks.getName() != null;
	}

	@Transactional
	@Override
	public void insertListOfTasks(ListOfTasks listOfTasks) {
		if (!validation(listOfTasks)) throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);

		String guidOfListOfTasks = listOfTasksDAO.insert(listOfTasks);
		if (guidOfListOfTasks == null) throw new NullPointerException(NPE);

		userAndListOfTasksDAO.insert(new UserAndListOfTasks(listOfTasks.getUserGuid(), guidOfListOfTasks));
	}

	@Transactional
	@Override
	public void insertListOfTasksBasedOnTheTemplate(ListOfTasks listOfTasks, String guidOfTemplateListOfTasks) {
		if (guidOfTemplateListOfTasks == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		String guidOfListOfTasks = listOfTasksDAO.insert(listOfTasks);
		if (guidOfListOfTasks == null) throw new NullPointerException(NPE);

		userAndListOfTasksDAO.insert(new UserAndListOfTasks(listOfTasks.getUserGuid(), guidOfListOfTasks));

		//наполняем только что созданный список задач задачи из списка шаблона
		listOfTasksDAO.fillingData(guidOfListOfTasks, guidOfTemplateListOfTasks);
	}

	@Transactional
	@Override
	public void insertListsOfTasks(Collection<ListOfTasks> listOfTasksCollection) {
		for (ListOfTasks listOfTasks : listOfTasksCollection) insertListOfTasks(listOfTasks);
	}

	@Override
	public ListOfTasks findListOfTasksByGuid(String guidOfListOfTasks) {
		if (guidOfListOfTasks == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		ListOfTasks listOfTasks = listOfTasksDAO.findListOfTasksByGuid(guidOfListOfTasks);
		if (listOfTasks == null) throw new NullPointerException(NPE);

		return listOfTasks;
	}

	@Override
	public Collection<ListOfTasks> findAllListOfTasksOfUser(String guidOfUser) {
		if (guidOfUser == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		Collection<String> listOfGuidOfListOfTasks = userAndListOfTasksDAO.findListOfTasksGuidByUserGuid(guidOfUser);
		if (listOfGuidOfListOfTasks == null) throw new NullPointerException(NPE);

		Collection<ListOfTasks> listOfListOfTasks = new ArrayList<>();
		for (String guidOfListOfTasks : listOfGuidOfListOfTasks) {
			ListOfTasks listOfTasks = listOfTasksDAO.findListOfTasksByGuid(guidOfListOfTasks);
			listOfListOfTasks.add(listOfTasks);
		}

		return listOfListOfTasks;
	}

	@Transactional
	@Override
	public void updateListOfTasks(String guidOfUser, ListOfTasks listOfTasks) throws UserIsNotOwnerOfListOfTasksException {
		if (guidOfUser == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);
		if (listOfTasks.getGuid() == null && !validation(listOfTasks))
			throw new IllegalArgumentException(THE_OBJECT_IS_NOT_VALID);

		String guidOfOwnerOfListOfTasks = listOfTasksDAO.findGuidOfOwnerOfListOfTasks(listOfTasks.getGuid());

		if (guidOfOwnerOfListOfTasks == null) throw new NullPointerException(NPE);

		if (!Objects.deepEquals(guidOfUser, guidOfOwnerOfListOfTasks))
			throw new UserIsNotOwnerOfListOfTasksException();

		listOfTasksDAO.update(listOfTasks);
	}

	@Transactional
	@Override
	public void deleteListOfTasks(String guidOfListOfTasks, String guidOfUser) throws UserIsNotOwnerOfListOfTasksException {
		if (guidOfListOfTasks == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);
		if (guidOfUser == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		String guidOfOwnerOfListOfTasks = listOfTasksDAO.findGuidOfOwnerOfListOfTasks(guidOfListOfTasks);

		if (guidOfOwnerOfListOfTasks == null) throw new NullPointerException(NPE);

		if (!Objects.deepEquals(guidOfUser, guidOfOwnerOfListOfTasks))
			throw new UserIsNotOwnerOfListOfTasksException();


		userAndListOfTasksDAO.deleteByGuidOfListOfTasks(guidOfListOfTasks);
		taskDAO.deleteByGuidOfListOfTasks(guidOfListOfTasks);
		listOfTasksDAO.delete(guidOfListOfTasks);
	}

	@Override
	public void subscribeUserToListOfTasks(String guidOfListOfTasks, String guidOfUser) {
		if (guidOfListOfTasks == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);
		if (guidOfUser == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		userAndListOfTasksDAO.insert(new UserAndListOfTasks(guidOfUser, guidOfListOfTasks));
	}

	@Override
	public void unsubscribeUserToListOfTasks(String guidOfListOfTasks, String guidOfUser) {
		if (guidOfListOfTasks == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);
		if (guidOfUser == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		String guidOfOwnerOfListOfTasks = listOfTasksDAO.findGuidOfOwnerOfListOfTasks(guidOfListOfTasks);

		if (guidOfOwnerOfListOfTasks == null) throw new NullPointerException(NPE);

		if (Objects.deepEquals(guidOfUser, guidOfOwnerOfListOfTasks))
			throw new IllegalStateException(ADMIN_OF_LIST_CANNOT_UNSUBSCRIBE_FROM_LIST);

		userAndListOfTasksDAO.delete(guidOfListOfTasks, guidOfUser);
	}

	@Override
	public Collection<User> getAllSubscribersForListOfTask(String guidOfListOfTask) {
		if (guidOfListOfTask == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		Collection<User> listOfUsersOfListOfTasks = listOfTasksDAO.getAllSubscribersForListOfTask(guidOfListOfTask);

		if (listOfUsersOfListOfTasks == null) throw new NullPointerException(NPE);

		return listOfUsersOfListOfTasks;
	}

	@Override
	public Collection<User> getAllUnsignedForListOfTask(String guidOfListOfTask, String guidOfUser) {
		if (guidOfListOfTask == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);
		if (guidOfUser == null) throw new IllegalArgumentException(GUID_FIELD_IS_NOT_SET);

		Collection<User> listOfUsersOfListOfTasks = listOfTasksDAO.getAllUnsignedForListOfTask(guidOfListOfTask, guidOfUser);

		if (listOfUsersOfListOfTasks == null) throw new NullPointerException(NPE);

		return listOfUsersOfListOfTasks;
	}
}
