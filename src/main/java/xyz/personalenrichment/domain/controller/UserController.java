package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.UserDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;
import xyz.personalenrichment.domain.tx.DBTXResponse;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserDao userDao;

	/* collections */
	// read a list of users
	@RequestMapping(method= RequestMethod.GET) 
	public @ResponseBody List<User> getUsers() {
		return userDao.readUsers();
	}
	
	/* units */
	// read a user
	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable Integer pk) {
		return userDao.readUser(pk);
	}
	
	// create a new user
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody User postUser(@RequestBody User user) {
		return userDao.createUser(user);
	}
	
	// update an existing user
	@RequestMapping(value = "/{pk}", method = RequestMethod.PATCH)
	public @ResponseBody User patchUser(@PathVariable Integer pk, @RequestBody User user) {
		return userDao.updateUser(pk, user);
	}	
	
	// delete a user
	@RequestMapping(value = "/{pk}", method = RequestMethod.DELETE)
	public @ResponseBody User deleteUser(@PathVariable Integer pk) {
		return userDao.deleteUser(pk);
	}
	
	/* relations */
	// get matches list for a user
	@RequestMapping(value = "/{pk}/matches", method = RequestMethod.GET)
	public @ResponseBody List<Move> getMatches(@PathVariable Integer pk) {
		return userDao.readMatches(pk);
	}
}
