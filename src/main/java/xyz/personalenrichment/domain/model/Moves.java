package xyz.personalenrichment.domain.model;
// Generated Jul 9, 2016 8:25:03 AM by Hibernate Tools 5.1.0.Alpha1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Moves generated by hbm2java
 */
@Entity
@Table(name = "moves", catalog = "domain_game")
public class Moves implements java.io.Serializable {

	private int idmoves;
	private @JsonIgnore Matches matches;
	private byte x;
	private byte y;

	public Moves() {
	}

	public Moves(int idmoves, Matches matches, byte x, byte y) {
		this.idmoves = idmoves;
		this.matches = matches;
		this.x = x;
		this.y = y;
	}

	@Id

	@Column(name = "idmoves", unique = true, nullable = false)
	public int getIdmoves() {
		return this.idmoves;
	}

	public void setIdmoves(int idmoves) {
		this.idmoves = idmoves;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id", nullable = false)
	public Matches getMatches() {
		return this.matches;
	}

	public void setMatches(Matches matches) {
		this.matches = matches;
	}

	@Column(name = "x", nullable = false)
	public byte getX() {
		return this.x;
	}

	public void setX(byte x) {
		this.x = x;
	}

	@Column(name = "y", nullable = false)
	public byte getY() {
		return this.y;
	}

	public void setY(byte y) {
		this.y = y;
	}

}
