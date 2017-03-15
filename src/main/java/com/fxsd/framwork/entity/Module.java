package com.fxsd.framwork.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 功能模块实体类
 * @author ChenJianhui
 */
@Entity
@Table(name = "sys_module")
public class Module extends BaseEntity<Module>{

	private static final long serialVersionUID = 2494286426769294101L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", length = 15)
	private String name;
	
	@Column(name = "remark", length = 100)
	private String remark;
	
	@Transient
	private List<Resource> resources;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	

}
