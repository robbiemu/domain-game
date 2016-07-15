package xyz.selfenrichment.spring_data_rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="moves")
@Data
public class Move extends BaseModel {
	@ManyToOne Match match;

	@ManyToOne User player;
	
	@Column(name="point_a_x", columnDefinition="TINYINT") Short pointAX;
	@Column(name="point_a_y", columnDefinition="TINYINT") Short pointAY;
	@Column(name="point_b_x", columnDefinition="TINYINT") Short pointBX;
	@Column(name="point_b_y", columnDefinition="TINYINT") Short pointBY;
	
	@Column(name="resulting_board", length=4096) 
	@Lob 
	Byte[] resulting_board;
}
