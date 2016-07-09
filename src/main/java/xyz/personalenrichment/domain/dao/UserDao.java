package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.User;
import xyz.personalenrichment.domain.tx.DBTXResponse;


public interface UserDao {
	public List<User> indexUsers();
	public User getUserById(Short pk);
/*	public Users createUser(); */
	public User updateUser(Short pk, String criteria); 
	public DBTXResponse deleteUser(Short pk);
}
