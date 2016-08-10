package cn.com.titans.tcsmp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import cn.com.titans.tcsmp.utils.StringUtils;

@Entity
public class ChargePileStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5058901758121895057L;

	public ChargePileStatus() {
	}

	@GeneratedValue
	@Id
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "chargePileId", nullable = false)
	private ChargePile chargePile;

	public ChargePile getChargePile() {
		return chargePile;
	}

	public void setChargePile(ChargePile chargePile) {
		this.chargePile = chargePile;
	}

	@Column(columnDefinition = "varchar(10) default 0", nullable = false)
	private String status;

	private String reasonCode;
	@Transient
	private String reasonMsg;

	@Column(columnDefinition = "timestamp")
	private Date updateDate;

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;

	}
	public String getReasonMsg() {
		this.reasonMsg = parseCode(this.reasonCode);
		
		return reasonMsg;
	}
	

	public static String parseCode(String reasonCode) {
		String msg="";
		if (!StringUtils.isEmpty(reasonCode)) {
			switch (reasonCode) {
			case "101":
				msg = "电压异常";
				break;
			case "102":
				msg = "电流异常";
				break;
			case "103":
				msg = "其他异常";
				break;
			case "201":
				msg = "急停故障";
				break;
			case "202":
				msg = "电表故障";
				break;
			case "203":
				msg = "接触器故障";
				break;
			case "204":
				msg = "读卡器故障";
				break;
			case "205":
				msg = "内部过温故障";
				break;
			case "206":
				msg = "连接器故障";
				break;
			case "207":
				msg = "绝缘故障";
				break;
			case "208":
				msg = "BMS故障";
				break;
			default:
				msg = "";
				break;
			}
		}
		return msg;
	}

	public void setReasonMsg(String reasonMsg) {
		this.reasonMsg = reasonMsg;
	}
}
