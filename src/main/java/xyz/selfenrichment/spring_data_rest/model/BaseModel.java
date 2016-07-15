package xyz.selfenrichment.spring_data_rest.model;

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

import lombok.Data;

@MappedSuperclass
@Data
public class BaseModel {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "INT UNSIGNED", unique=true, nullable=false)
	long id;

    @Version
    @Column(name = "version")
    private int version = 0;
 
    @Column(name = "last_updated", insertable = false, updatable = false,
    		columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Generated(value=GenerationTime.ALWAYS) 
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated; 

    @Column(name="created", insertable=false, updatable=false, 
    		columnDefinition= "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
    @Generated(value = GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP) 
    private Date created;

}