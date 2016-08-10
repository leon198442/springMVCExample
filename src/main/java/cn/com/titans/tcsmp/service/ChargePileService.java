package cn.com.titans.tcsmp.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import cn.com.titans.tcsmp.dao.IChargePileDao;
import cn.com.titans.tcsmp.entity.ChargePile;

@Service("ChargePileService")
public class ChargePileService extends AbstractService<ChargePile> {

	@Resource
	private IChargePileDao dao;

	public ChargePileService() {
		super();
	}

	public List<ChargePile> findByStatus(String status) {
		return dao.findByStatus(status);
	}

	public int countByStatus(String status) {
		return dao.countByStatus(status);
	}

	@Override
	protected JpaSpecificationExecutor<ChargePile> getJSEDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	protected JpaRepository<ChargePile, Long> getJPDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public long countAll() {
		// TODO Auto-generated method stub
		return dao.count();
	}

	public List<ChargePile> findByUpdateDate(Date date) {
		return dao.findByUpdateDate(date);
	}

//	@SuppressWarnings("unchecked")
//	public Page<ChargePile> findPageWithParameters(Page<ChargePile> page) {
//		page = super.findPageWithParameters(page);
//		List<ChargePile> cpList = page.getItems();
//		String query = "select * from (select charge_pile_id as chargePileId,status from charge_pile_info order by update_date desc) as a group by chargePileId";
//		Session session = (Session) em.getDelegate();
//
//		Query q = session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(ChargePileStatus.class));
//
//		List<ChargePileStatus> t = q.list();
//		for (ChargePileStatus s : t) {
//			for (ChargePile cp : cpList) {
//
//				if (cp.getId() == s.getChargePileId().longValue()) {
//					cp.setStatus(s.getStatus());
//					this.save(cp);
//					continue;
//				}
//
//			}
//
//		}
//		return page;
//	}
//	@SuppressWarnings("unchecked")
//	public void updateChargePileStatus() {
//
//		String query = "select * from (select charge_pile_id as chargePileId,status from charge_pile_info order by update_date desc) as a group by chargePileId";
//		Session session = (Session) em.getDelegate();
//
//		Query q = session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(ChargePileStatus.class));
//
//		List<ChargePileStatus> t = q.list();
//		for (ChargePileStatus s : t) {
//			ChargePile cp = findOne(s.getChargePileId().longValue());
//			cp.setStatus(s.getStatus());
//			save(cp);
//
//		}
//
//		
//
//	}

}
