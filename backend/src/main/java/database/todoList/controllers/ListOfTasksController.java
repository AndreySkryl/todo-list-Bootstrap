package database.todoList.controllers;

import database.todoList.model.ListOfTasks;
import database.todoList.model.User;
import database.todoList.services.ListOfTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.EMPTY_LIST;

@RestController
@RequestMapping("/list_of_tasks")
public class ListOfTasksController {
	@Autowired private ListOfTasksService listOfTasksService;

	@RequestMapping(value = "/add/one", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newListOfTasks(@RequestBody ListOfTasks listOfTasks) {
		try {
			listOfTasksService.insertListOfTasks(listOfTasks);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/add/one/basedOnTemplate", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newListsOfTasksBasedOnTheTemplate(
			@RequestBody ListOfTasks listOfTasks,
			@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfTemplateListOfTasks) {
		try {
			listOfTasksService.insertListOfTasksBasedOnTheTemplate(listOfTasks, guidOfTemplateListOfTasks);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newListsOfTasks(@RequestBody Collection<ListOfTasks> listOfTasksCollection) {
		try {
			listOfTasksService.insertListsOfTasks(listOfTasksCollection);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<ListOfTasks> getListOfTasks(@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTasks) {
		try {
			ListOfTasks listOfTasks = listOfTasksService.findListOfTasksByGuid(guidOfListOfTasks);
			return new ResponseEntity<>(listOfTasks, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(new ListOfTasks(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<ListOfTasks>> getAllListsOfTasksOfUser(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			ResponseEntity<Collection<ListOfTasks>> responseEntity = new ResponseEntity<>(listOfTasksService.findAllListOfTasksOfUser(guidOfUser), HttpStatus.OK);
			return responseEntity;
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<ListOfTasks>>(EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/edit/one", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateListOfTasks(@RequestParam(User.GUID_OF_USER) String guidOfUser,
										@RequestBody ListOfTasks listOfTasks) {
		try {
			listOfTasksService.updateListOfTasks(guidOfUser, listOfTasks);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteListOfTasks(@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTasks,
																@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			listOfTasksService.deleteListOfTasks(guidOfListOfTasks, guidOfUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> subscribe(@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTasks,
								@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			listOfTasksService.subscribeUserToListOfTasks(guidOfListOfTasks, guidOfUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/unsubscribe", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> unsubscribe(@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTasks,
								  @RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			listOfTasksService.unsubscribeUserToListOfTasks(guidOfListOfTasks, guidOfUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all_subscribers", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getAllSubscribers(
			@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTask) {
		try {
			Collection<User> allSubscribersForListOfTask = listOfTasksService.getAllSubscribersForListOfTask(guidOfListOfTask);
			User.convertFROMPathToPhotoOfUserTOBase64StringFromImage(allSubscribersForListOfTask);
			return new ResponseEntity<>(allSubscribersForListOfTask, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all_unsigned", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getAllUnsignedForListOfTask(
			@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTask,
			@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			Collection<User> allUnsignedForListOfTask = listOfTasksService.getAllUnsignedForListOfTask(guidOfListOfTask, guidOfUser);
			User.convertFROMPathToPhotoOfUserTOBase64StringFromImage(allUnsignedForListOfTask);
			return new ResponseEntity<>(allUnsignedForListOfTask, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
