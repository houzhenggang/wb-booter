package com.yaoa.boot.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity基类
 * @author ChenJianhui
 */
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable{

	private static final long serialVersionUID = 5516293384661833901L;
	

	@JsonIgnore
	@Column(name = "deleted")
	private boolean deleted = false;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public abstract Long getId() ;
	
	public abstract void setId(Long id);
	
}