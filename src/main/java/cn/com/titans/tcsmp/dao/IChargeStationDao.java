package cn.com.titans.tcsmp.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.com.titans.tcsmp.entity.ChargeStation;

@Repository("ChargeStationDao")
public interface IChargeStationDao extends JpaRepository<ChargeStation,Long>,JpaSpecificationExecutor<ChargeStation> {

	@Query("select cs from ChargeStation cs where updateDate >= ?1")
	List<ChargeStation> findByUpdateDate(Date date);

}
