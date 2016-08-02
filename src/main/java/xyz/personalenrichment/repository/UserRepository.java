package xyz.personalenrichment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.personalenrichment.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findOneByUsername(String username);
	User findOneByUsernameAndPassword(String username, String password);
	boolean existsByUsernameAndPassword(String username, String password);
	default boolean isValidCredentials(User txu) {
		return this.existsByUsernameAndPassword(txu.getUsername(), txu.getPassword());
	}
	default boolean isValidUserDetailsForLogin(User txu) {
		return ( (txu.getUsername() != null) && (txu.getPassword() != null) );
	}
}
