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
public class ChargePile implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7570604470599170261L;



	public ChargePile() {
		
	}


	@GeneratedValue
	@Id
	private long id;  
    private String fullName;
    
   // @Column(columnDefinition="varchar(10) default 0")
   
    /*
     * 0:故障  1:正常  2:空闲  3:未知
     * 
     * 
     */
    @Column(columnDefinition="varchar(10) default 3")
    private String status;
	

	
	@Column(columnDefinition="timestamp")
	private Date updateDate;
	
	@ManyToOne
	@JoinColumn(name="chargeStationId") 
	private ChargeStation chargeStation;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}



	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


//	public long getChargeStationId() {
//		return chargeStationId;
//	}
//
//
//	public void setChargeStationId(long chargeStationID) {
//		this.chargeStationId = chargeStationID;
//	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public ChargeStation getChargeStation() {
		return chargeStation;
	}


	public void setChargeStation(ChargeStation chargeStation) {
		this.chargeStation = chargeStation;
	}



}
