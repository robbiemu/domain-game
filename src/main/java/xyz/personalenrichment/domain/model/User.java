package xyz.personalenrichment.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="users")
@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseModel {
	@Column String username;
	@Column String display;
	@Column String email;
	@Column String password;	
}
