package cn.com.titans.tcsmp.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import cn.com.titans.tcsmp.dao.IChargePileInfoDao;
import cn.com.titans.tcsmp.entity.ChargePileInfo;

@Service("ChargePileInfoService")
public class ChargePileInfoService extends AbstractService<ChargePileInfo> {
	@Resource
	private IChargePileInfoDao dao;

	@Override
	protected JpaSpecificationExecutor<ChargePileInfo> getJSEDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	protected JpaRepository<ChargePileInfo, Long> getJPDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<ChargePileInfo> findAllOrderByUpdateDate(){
		return dao.findAllOrderByUpdateDate();
	}
	
	public List<ChargePileInfo> findById(long id){
		return dao.findById(id);
	}

	public List<ChargePileInfo> findByChargePileId(Long id) {
		// TODO Auto-generated method stub
		return dao.findByChargePileId(id);
	}

	public List<ChargePileInfo> findByChargePileIdAndStartDate(Long id, Date start) {

		Date end = new Date();
		end.setTime(start.getTime() + 24*3600000);// +1day

		return dao.findByChargePileIdAndDate(id,start,end);
	}

}
