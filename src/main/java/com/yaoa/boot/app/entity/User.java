package com.yaoa.boot.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="sys_user")
public class User extends BaseEntity<User>{

	private static final long serialVersionUID = -8466410893638830526L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "username", length = 50)
	private String username;

	@JsonIgnore
	private String password;

	@Column(name = "phone", length = 11, unique = true)
	private String phone;

	@Email
	@Column(name = "email", length = 50)
	private String email;

	/**
	 * 锁定：false
	 */
	@Column(name = "locked")
	private boolean locked = false;

	/**
	 * 用户注册时间
	 */
	@Column(name = "add_time")
	private Date addTime = new Date();
	
	/**
	 * 用户所属的角色
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
