package com.fxsd.framwork.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单实体类O
 * @author ChenJianhui
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Menu>{

	private static final long serialVersionUID = 2494286426769294101L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", length = 15)
	private String name;
	
	@Column(name = "icon", length = 100)
	private String icon;
	
	@Column(name = "resourceId")
	private Long resourceId;
	
	/**
	 * 描点
	 */
	@Column(name = "anchor", length = 30)
	private String anchor;
	
	/**
	 * 父菜单ID
	 */
	@Column(name = "parentId")
	private Long parentId;
	
	/**
	 * 父菜单名称
	 */
	@Column(name = "parent_name")
	private String parentName;

	/**
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;

	@Transient
	private List<Menu> childMenus;



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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	

}
