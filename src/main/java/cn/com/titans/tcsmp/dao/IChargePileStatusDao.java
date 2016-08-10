package cn.com.titans.tcsmp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.com.titans.tcsmp.entity.ChargePileStatus;

@Repository("ChargePileStatus")
public interface IChargePileStatusDao
		extends JpaRepository<ChargePileStatus, Long>, JpaSpecificationExecutor<ChargePileStatus> {

	List<ChargePileStatus> findByChargePileId(Long id);

}
