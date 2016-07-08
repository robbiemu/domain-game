package xyz.personalenrichment.domain.model;
// Generated Jul 8, 2016 9:14:20 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "domain_game", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users implements java.io.Serializable {

	private Integer idusers;
	private String displayName;
	private String loginName;
	private String password;
	private String email;
	private @JsonIgnore Set<Matches> matchesesForPlayerBId = new HashSet<Matches>(0);
	private @JsonIgnore Set<Matches> matchesesForPlayerAId = new HashSet<Matches>(0);

	public Users() {
	}

	public Users(String displayName, String loginName, String password, String email) {
		this.displayName = displayName;
		this.loginName = loginName;
		this.password = password;
		this.email = email;
	}

	public Users(String displayName, String loginName, String password, String email,
			Set<Matches> matchesesForPlayerBId, Set<Matches> matchesesForPlayerAId) {
		this.displayName = displayName;
		this.loginName = loginName;
		this.password = password;
		this.email = email;
		this.matchesesForPlayerBId = matchesesForPlayerBId;
		this.matchesesForPlayerAId = matchesesForPlayerAId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idusers", unique = true, nullable = false)
	public Integer getIdusers() {
		return this.idusers;
	}

	public void setIdusers(Integer idusers) {
		this.idusers = idusers;
	}

	@Column(name = "display_name", nullable = false, length = 127)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "login_name", nullable = false, length = 127)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByPlayerBId")
	public Set<Matches> getMatchesesForPlayerBId() {
		return this.matchesesForPlayerBId;
	}

	public void setMatchesesForPlayerBId(Set<Matches> matchesesForPlayerBId) {
		this.matchesesForPlayerBId = matchesesForPlayerBId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usersByPlayerAId")
	public Set<Matches> getMatchesesForPlayerAId() {
		return this.matchesesForPlayerAId;
	}

	public void setMatchesesForPlayerAId(Set<Matches> matchesesForPlayerAId) {
		this.matchesesForPlayerAId = matchesesForPlayerAId;
	}

}