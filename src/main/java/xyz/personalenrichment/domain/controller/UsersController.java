package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.UsersDao;
import xyz.personalenrichment.domain.model.Matches;
import xyz.personalenrichment.domain.model.Users;
import xyz.personalenrichment.domain.tx.DBTXResponse;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersDao usersDao;

	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<Users> index() {
		return usersDao.indexUsers();
	}
	
	@RequestMapping(value = "/byId/{pk}", method = RequestMethod.GET)
	public @ResponseBody Users byId(@PathVariable Short pk) {
		return usersDao.getUserById(pk);
	}
	
	@RequestMapping(method= RequestMethod.POST) //root
	public @ResponseBody Users create() {
		return usersDao.createUser();
	}

	@RequestMapping(value= "/{pk}", method= RequestMethod.PATCH) //root
	public @ResponseBody Users update(@PathVariable Short pk) {
		return usersDao.updateUser(pk);
	}
	
	@RequestMapping(value= "/{pk}", method= RequestMethod.DELETE) //root
	public @ResponseBody DBTXResponse delete(@PathVariable Short pk) {
		return usersDao.deleteUser(pk);
	}
}
