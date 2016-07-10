package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;
import xyz.personalenrichment.domain.tx.DBTXResponse;


public interface UserDao {
	public List<User> readUsers();
	public User readUser(Integer pk);
	public User createUser(User user);
	public User updateUser(Integer pk, User user);
	public User deleteUser(Integer pk);
	public List<Move> readMatches(Integer pk);
}
