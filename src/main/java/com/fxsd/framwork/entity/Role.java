package com.fxsd.framwork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 角色实体类
 * @author ChenJianhui
 */
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity<Role>{

	private static final long serialVersionUID = 1572456459541801667L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name ="name", length = 50)
	private String name;

	/**
	 * 该角色拥有的权限
	 */
	@ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
	@JoinTable(name = "role_resource", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "resource_id", referencedColumnName = "id") })
	private List<Resource> resources;
	

	@JsonFormat(pattern = "yyyy-MM-dd HH:dd:ss", timezone="GMT+8")
	@Column(name = "create_time")
	private Date createTime;


	public Long getId() {
		return id;
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

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
