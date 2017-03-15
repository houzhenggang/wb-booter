package com.fxsd.framwork.entity;

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

}