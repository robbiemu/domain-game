package xyz.personalenrichment.domain.model;
// Generated Jul 9, 2016 6:12:51 PM by Hibernate Tools 5.1.0.Alpha1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MatchPlayerJt generated by hbm2java
 */
@Entity
@Table(name = "match_player_jt", catalog = "domain_game")
public class MatchPlayerJt implements java.io.Serializable {

	private Integer idmatchPlayers;
	private Match match;
	private User user;

	public MatchPlayerJt() {
	}

	public MatchPlayerJt(Match match, User user) {
		this.match = match;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idmatch_players", unique = true, nullable = false)
	public Integer getIdmatchPlayers() {
		return this.idmatchPlayers;
	}

	public void setIdmatchPlayers(Integer idmatchPlayers) {
		this.idmatchPlayers = idmatchPlayers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id", nullable = false)
	public Match getMatch() {
		return this.match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}