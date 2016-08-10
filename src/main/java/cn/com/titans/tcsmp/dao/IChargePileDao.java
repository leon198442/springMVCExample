package cn.com.titans.tcsmp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.com.titans.tcsmp.entity.ChargePile;
@Repository("ChargePileDao")
public interface IChargePileDao extends JpaRepository<ChargePile,Long>,JpaSpecificationExecutor<ChargePile> {

	List<ChargePile> findByStatus(String status);
	
	
	int countByStatus(String status);
	
	@Query("select cp from ChargePile cp where updateDate >= ?1")
	public List<ChargePile> findByUpdateDate(Date date);
	
}
