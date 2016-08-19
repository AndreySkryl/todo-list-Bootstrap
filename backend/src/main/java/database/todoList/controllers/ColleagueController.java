package database.todoList.controllers;

import database.todoList.model.Colleague;
import database.todoList.model.User;
import database.todoList.services.ColleagueService;
import database.todoList.services.UserService;
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
@RequestMapping("/colleague")
public class ColleagueController {
	@Autowired private ColleagueService colleagueService;
	@Autowired private UserService userService;

	@RequestMapping(value = "/add/one", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newColleague(@RequestParam(User.GUID_OF_USER) String guidOfUser,
												   @RequestParam(Colleague.GUID_OF_COLLEAGUE) String guidOfColleague) {
		try {
			colleagueService.insertConnectionBetweenColleagues(guidOfUser, guidOfColleague);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser,
									@RequestBody Collection<String> guidOfColleagues) {
		try {
			colleagueService.insertConnectionBetweenColleagues(guidOfUser, guidOfColleagues);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all/guides", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<String>> getGuidOfColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			Collection<String> listOfGuidOfColleagues = colleagueService.findGuidesOfColleaguesByUserGuid(guidOfUser);
			return new ResponseEntity<>(listOfGuidOfColleagues, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<String>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/all/as/user", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getAllColleaguesAsUsers(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			Collection<String> listOfGuidOfColleagues = getGuidOfColleagues(guidOfUser).getBody();
			Collection<User> listOfColleagues = userService.findUsersByGuid(listOfGuidOfColleagues);
			return new ResponseEntity<>(listOfColleagues, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteOneColleague(@RequestParam(User.GUID_OF_USER) String guidOfUser,
										 @RequestParam(Colleague.GUID_OF_COLLEAGUE) String guidOfColleague) {
		try {
			colleagueService.deleteConnectionBetweenColleagues(guidOfUser, guidOfColleague);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteSomeColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser, @RequestBody Collection<String> guidOfColleagues) {
		try {
			colleagueService.deleteConnectionBetweenColleagues(guidOfUser, guidOfColleagues);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteAllColleagues(@RequestParam(User.GUID_OF_USER) String guidOfUser) {
		try {
			colleagueService.deleteConnectionsWithAllColleagues(guidOfUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}