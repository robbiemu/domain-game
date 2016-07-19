package xyz.selfenrichment.spring_data_rest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="matches")
@Data
public class Match extends BaseModel {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completed_on")
	private Date completedOn;
	
	@ManyToOne User winner;
	
	@ManyToOne @JoinColumn(name = "first_player") User firstPlayer;
	@ManyToOne @JoinColumn(name = "second_player") User second_player;
	
	@Column(name = "starting_board", length=4096)
	@Lob
	Byte[] startingBoard;
	
}
