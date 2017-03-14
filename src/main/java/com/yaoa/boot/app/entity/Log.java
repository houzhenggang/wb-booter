package com.yaoa.boot.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志实体
 * @author ChenJianhui
 */
@Entity
@Table(name = "sys_log")
public class Log implements Serializable{

	private static final long serialVersionUID = 6177768198471924345L;

	@Id
	private String id;

	/**
	 * 操作人
	 */
	@Column(name = "operator_id")
	private Long operatorId;// 操作人
	
	/**
	 * 操作人名称
	 */
	@Column(name = "operator_name", length = 50)
	private String operatorName;
	
	/**
	 * 操作名称
	 */
	@Column(name = "oper_name", length = 50)
	private String operName;
	
	/**
	 * 操作参数
	 */
	@Column(name = "oper_params", length = 2048)
	private String operParams;
	
	/**
	 * 操作结果
	 */
	@Column(name = "resultCode")
	private int resultCode;
	
	/**
	 *  结果消息
	 */
	@Column(name = "resultMsg", length = 512)
	private String resultMsg;
	
	/**
	 * 操作客户端ip
	 */
	@Column(name = "client_ip", length = 32)
	private String clientIp;
	
	/**
	 * 操作时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
	@Column(name = "oper_time")
	private Date operTime = new Date();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperParams() {
		return operParams;
	}

	public void setOperParams(String operParams) {
		this.operParams = operParams;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}



	
}
