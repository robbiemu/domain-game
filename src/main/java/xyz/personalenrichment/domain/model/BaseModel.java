package xyz.personalenrichment.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=false)
 class BaseModel {
	@JsonIgnore
	@Id
	@GeneratedValue
	@Column(columnDefinition = "INT UNSIGNED", unique=true, nullable=false)
	long id;

	@JsonIgnore
    @Version
    @Column(name = "version")
    private int version = 0;
 
	@JsonIgnore
    @Column(name = "last_updated", insertable = false, updatable = false,
    		columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Generated(value=GenerationTime.ALWAYS) 
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated; 

	@JsonIgnore
    @Column(name="created", insertable=false, updatable=false, 
    		columnDefinition= "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
    @Generated(value = GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP) 
    private Date created;

}