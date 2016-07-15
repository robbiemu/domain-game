package xyz.selfenrichment.spring_data_rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User extends BaseModel {
	@Column String username;
	@Column String display;
	@Column String email;
	@Column String password;
	
	
}
