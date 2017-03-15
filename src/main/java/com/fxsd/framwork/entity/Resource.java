package com.fxsd.framwork.entity;


import javax.persistence.*;

/**
 * 系统资源实体类
 * 
 * @author ChenJianhui
 */
@Entity
@Table(name = "sys_resource")
public class Resource extends BaseEntity<Resource> {

	private static final long serialVersionUID = 9059206230543811927L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "url", length = 80)
	private String url;

	@Column(name = "name", length = 50)
	private String name;

	/**
	 * 权限位
	 */
	@Column(name = "position", nullable = false)
	private Integer position;

	/**
	 * 权限码
	 */
	@Column(name = "code", nullable = false)
	private Long code;
	
	/**
	 * 权限控制级别 
	 * 0: 完全对外开放
	 * 1: 登陆即可访问
	 * 2: 需要授权访问
	 */
	@Column(name = "right_level")
	private int rightLevel = 2;

	/**
	 * 所属模块
	 */
	@Column(name = "module_id")
	private Long moduleId;

	/**
	 * 是否限制恶意访问
	 */
	@Column(name = "limited")
	private boolean limited = false;
	
	/**
	 * 一段时间内的访问次数
	 */
	@Column(name = "frequency")
	private Integer frequency;
	
	/**
	 * 时间段（以秒为单位）
	 */
	@Column(name = "interval_time")
	private Integer interval;

	/**
	 * 描述
	 */
	@Column(name = "description", length = 100)
	private String remark;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Long getCode() {
		return this.code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public int getRightLevel() {
		return rightLevel;
	}

	public void setRightLevel(int rightLevel) {
		this.rightLevel = rightLevel;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isLimited() {
		return limited;
	}

	public void setLimited(boolean limited) {
		this.limited = limited;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	
	
}
