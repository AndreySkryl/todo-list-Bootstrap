package database.todoList.controllers;

import database.todoList.model.ListOfTasks;
import database.todoList.model.Task;
import database.todoList.services.TaskService;
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

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired private TaskService taskService;

	@RequestMapping(value = "/add/one", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newTask(@RequestBody Task task) {
		try {
			taskService.insertTask(task);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newTasks(@RequestBody Collection<Task> tasks) {
		try {
			taskService.insertTasks(tasks);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Task> getTaskByGuid(@RequestParam(Task.GUID_OF_TASK) String guidOfTask) {
		try {
			return new ResponseEntity<>(taskService.findTaskByGuid(guidOfTask), HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<Task>> getAllTasksOfListOfTasks(
			@RequestParam(ListOfTasks.GUID_OF_LIST_Of_TASKS) String guidOfListOfTasks) {
		try {
			return new ResponseEntity<>(taskService.findAllTasksOfListOfTasks(guidOfListOfTasks), HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<Task>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all/count", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCountOfListOfTasks() {
		try {
			return new ResponseEntity<>(taskService.findCountOfTasks(), HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/edit/one", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateTask(@RequestBody Task task) {
		try {
			taskService.updateTask(task);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteTask(@RequestParam(Task.GUID_OF_TASK) String guidOfTask) {
		try {
			taskService.deleteTask(guidOfTask);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteTasks(@RequestBody Collection<String> guidesOfTask) {
		try {
			taskService.deleteTasks(guidesOfTask);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
