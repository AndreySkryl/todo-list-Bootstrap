package database.todoList.controllers;

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
@RequestMapping("/user")
public class UserController {
	@Autowired private ColleagueService colleagueService;
	@Autowired private UserService userService;

	@RequestMapping(value = "/signUp", produces = "text/plain", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@RequestBody User user) {
		try {
			return new ResponseEntity<>(userService.signUp(user), HttpStatus.ACCEPTED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/login", produces = "text/plain", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			return new ResponseEntity<>(userService.login(user), HttpStatus.ACCEPTED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/add/one", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newUser(@RequestBody User user) {
		try {
			userService.insertUser(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/add/some", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> newUsers(@RequestBody Collection<User> users) {
		try {
			userService.insertUsers(users);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/get/one", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByGuid(@RequestParam(User.GUID_OF_USER) String guid) {
		try {
			User user = userService.findUserByGuid(guid);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @RequestMapping(value = "/get/all", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> getAllUsers(@RequestParam(User.GUID_OF_USER) String guidOfUserSender) {
		try {
			Collection<String> guidesOfColleagues = colleagueService.findGuidesOfColleaguesByUserGuid(guidOfUserSender);

			Collection<User> allUsersWithoutUserSenderAndColleagues =
//					userService.f1(guidOfUserSender, guidesOfColleagues);
					userService.findAllUsersWithoutUserSenderAndColleagues(guidOfUserSender, guidesOfColleagues);

		return new ResponseEntity<>(allUsersWithoutUserSenderAndColleagues, HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<Collection<User>>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/edit/one", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
		try {
			userService.updateUser(user);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/one", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteUser(@RequestParam(User.GUID_OF_USER) String guid) {
		try {
			userService.deleteUser(guid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/delete/some", consumes = "application/json", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteUsers(@RequestBody Collection<String> guides) {
		try {
			userService.deleteUsers(guides);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Throwable exception) {
			System.err.println(exception.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
