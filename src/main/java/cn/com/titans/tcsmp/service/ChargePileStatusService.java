package cn.com.titans.tcsmp.service;

import java.util.List;

import javax.annotation.Resource;


import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import cn.com.titans.tcsmp.dao.IChargePileStatusDao;
import cn.com.titans.tcsmp.entity.ChargePileStatus;
@Service("ChargePileStatusService")
public class ChargePileStatusService extends AbstractService<ChargePileStatus> {
	@Resource
	private IChargePileStatusDao dao;
	@Override
	protected JpaSpecificationExecutor<ChargePileStatus> getJSEDao() {
		return dao;
	}

	@Override
	protected JpaRepository<ChargePileStatus, Long> getJPDao() {
		return dao;
	}

	public List<ChargePileStatus> findByChargePileId(Long id) {
		return dao.findByChargePileId(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReasonCount> getReasonCount(long id) {
		Session session = (Session) em.getDelegate();
		String query = "SELECT reason_code as reasonCode, count(*) as counts from charge_pile_status where charge_pile_id=:id and status=0 GROUP BY reason_code";
		if(id ==0){
			query = "SELECT reason_code as reasonCode, count(*) as counts from charge_pile_status where status=0 GROUP BY reason_code";
		}

		SQLQuery q = session.createSQLQuery(query);
		if(id !=0){
			q.setLong("id", id);
		}
		
		q.addScalar("reasonCode",  StandardBasicTypes.STRING);
		q.addScalar("counts",  StandardBasicTypes.INTEGER);
		q.setResultTransformer(Transformers.aliasToBean(ReasonCount.class));

		List<ReasonCount> t = q.list();
		
		return t;

	}
	


}
