package cn.com.titans.tcsmp.service;



import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;


import cn.com.titans.tcsmp.dao.IChargeStationDao;
import cn.com.titans.tcsmp.entity.ChargeStation;


@Service("ChargeStationService")
public class ChargeStationService extends AbstractService<ChargeStation>{

    @Resource
    private IChargeStationDao dao;


	public ChargeStationService() {
        super();
    }

	public long countAll(){
		return dao.count();
	}



	@Override
	protected JpaSpecificationExecutor<ChargeStation> getJSEDao() {
		// TODO Auto-generated method stub
		return dao;
	}


	@Override
	protected JpaRepository<ChargeStation, Long> getJPDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public List<ChargeStation> findByUpdateDate(Date date) {
		// TODO Auto-generated method stub
		return dao.findByUpdateDate(date);
	}







}