package cn.com.titans.tcsmp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class ChargeStation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7084347318309553395L;




	public ChargeStation() {

	}


	@GeneratedValue
	@Id
	private long id;

	@Column(columnDefinition="timestamp")
	private Date updateDate;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	private String fullName, status;

	private String address;
	
	@OneToMany(cascade={CascadeType.REMOVE},mappedBy="chargeStation")
	private List<ChargePile> chargePileList;
	
	public List<ChargePile> getChargePileList() {
		return chargePileList;
	}


	public void setChargePileList(List<ChargePile> chargePileList) {
		this.chargePileList = chargePileList;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

}
