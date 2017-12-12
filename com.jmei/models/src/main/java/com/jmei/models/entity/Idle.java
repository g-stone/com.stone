package com.jmei.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_idle")
public class Idle {
	private String seedId;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "seed_id", columnDefinition = "varchar(40) comment '测试表主键ID'")
	public String getSeedId() {
		return seedId;
	}
	
	public void setSeedId(String seedId) {
		this.seedId = seedId;
	}
}
