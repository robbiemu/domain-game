package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Users;
import xyz.personalenrichment.domain.tx.DBTXResponse;


public interface UsersDao {
	public List<Users> indexUsers();
	public Users getUserById(Short pk);
	public Users createUser();
	public DBTXResponse deleteUser(Short pk);
	public Users updateUser(Short pk);
}
