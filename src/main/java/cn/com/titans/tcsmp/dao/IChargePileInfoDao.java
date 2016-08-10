
package cn.com.titans.tcsmp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import cn.com.titans.tcsmp.entity.ChargePileInfo;
@Repository("ChargePileInfoDao")
public interface IChargePileInfoDao extends JpaRepository<ChargePileInfo,Long>,JpaSpecificationExecutor<ChargePileInfo> {


	
	@Query("select cpi from ChargePileInfo cpi where updateDate >= ?1")
	public List<ChargePileInfo> findByUpdateDate(Date date);

	@Query("select cpi from ChargePileInfo cpi order by updateDate asc")
	public List<ChargePileInfo> findAllOrderByUpdateDate();


	List<ChargePileInfo> findById(long id);


	List<ChargePileInfo> findByChargePileId(Long id);

	@Query("select cpi from ChargePileInfo cpi where chargePile.id =?1 and updateDate >= ?2 and updateDate <?3 order by updateDate asc")
	List<ChargePileInfo> findByChargePileIdAndDate(Long id, Date start, Date end);
	
}