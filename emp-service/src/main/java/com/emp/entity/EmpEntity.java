package com.emp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmpEntity {

	@Id
	private Integer id;

	private String name;

	private String dept;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	
}
