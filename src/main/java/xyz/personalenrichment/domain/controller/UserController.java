package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.UserDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.User;
import xyz.personalenrichment.domain.tx.DBTXResponse;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserDao userDao;

	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<User> index() {
		return userDao.indexUsers();
	}
	
	@RequestMapping(value = "/byId/{pk}", method = RequestMethod.GET)
	public @ResponseBody User byId(@PathVariable Short pk) {
		return userDao.getUserById(pk);
	}
	
	/*
	 * TODO - find a way to successfully change the response status without throwing an error
	 * 
	 * The action performed by the POST method might not result in a resource that can be identified by a 
	 * URI. In this case, either 200 (OK) or 204 (No Content) is the appropriate response status, 
	 * depending on whether or not the response includes an entity that describes the result. 
	 *
	 * If a resource has been created on the origin server, the response SHOULD be 201 (Created) and 
	 * contain an entity which describes the status of the request and refers to the new resource, and a 
	 * Location header (see section 14.30). 
	 * 
	 * Responses to this method are not cacheable, unless the response includes appropriate Cache-Control 
	 * or Expires header fields. However, the 303 (See Other) response can be used to direct the user 
	 * agent to retrieve a cacheable resource. 
	 *
	 */
/*	@RequestMapping(value="?{criteria}", method= RequestMethod.POST) //root
	public @ResponseBody Users create(@PathVariable String criteria) {
		return usersDao.createUser(criteria);
	}

	@RequestMapping(value= "/{pk}?{criteria}", method= RequestMethod.PATCH) //root
	public @ResponseBody Users update(@PathVariable Short pk, @PathVariable String criteria) {
		return usersDao.updateUser(pk, criteria);
	} */
	
	/*
	 * TODO - find a way to successfully change the response status without throwing an error
	 * 
	 * A successful response SHOULD be 200 (OK) if the response includes an entity describing the status, 
	 * 202 (Accepted) if the action has not yet been enacted, or 204 (No Content) if the action has been 
	 * enacted but the response does not include an entity. 
	 */
	@RequestMapping(value= "/{pk}", method= RequestMethod.DELETE) //root
	public @ResponseBody DBTXResponse delete(@PathVariable Short pk) {
		return userDao.deleteUser(pk);
	}
}