package cn.com.titans.tcsmp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChargePileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3180827474999722326L;

	@GeneratedValue
	@Id
	private long id;



	@Column(columnDefinition = "float(10,2) default 0", nullable = false)
	private float chargedAmount;
	@Column(columnDefinition = "float(10,2) default 0", nullable = false)
	private float voltage;
	@Column(columnDefinition = "float(10,2) default 0", nullable = false)
	private float current;

	@Column(columnDefinition = "timestamp")
	private Date updateDate;

	@Column(columnDefinition = "datetime", nullable = false)
	private Date chargeStartTime;

	@ManyToOne
	@JoinColumn(name = "chargePileId", nullable = false)
	private ChargePile chargePile;

	public ChargePile getChargePile() {
		return chargePile;
	}

	public void setChargePile(ChargePile chargePile) {
		this.chargePile = chargePile;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getChargeStartTime() {
		return chargeStartTime;
	}

	public void setChargeStartTime(Date chargeStartTime) {
		this.chargeStartTime = chargeStartTime;
	}

	public ChargePileInfo() {

	}

	public float getChargedAmount() {
		return chargedAmount;
	}

	public void setChargedAmount(float chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	public float getVoltage() {
		return voltage;
	}

	public void setVoltage(float voltage) {
		this.voltage = voltage;
	}

	public float getCurrent() {
		return current;
	}

	public void setCurrent(float current) {
		this.current = current;
	}

}
