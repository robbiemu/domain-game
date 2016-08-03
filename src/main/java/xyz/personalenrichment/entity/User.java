package xyz.personalenrichment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class User {
	@Id @GeneratedValue private long id;
	
	@Column(unique=true, nullable=false) String username;
	@Column String password;
}
