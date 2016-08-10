package cn.com.titans.tcsmp.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;


import cn.com.titans.tcsmp.utils.JPAUtil;
import cn.com.titans.tcsmp.utils.Page;


@Transactional
public abstract class AbstractService<T extends Serializable>{
	
	@PersistenceContext  
    protected EntityManager em; 

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	protected abstract JpaSpecificationExecutor<T> getJSEDao();
	
	protected abstract JpaRepository<T,Long> getJPDao();

	
	public T findOne(final long id) {
		return getJPDao().findOne(id);
	}

	
	public List<T> findAll() {
		return getJPDao().findAll();
	}

	
	public void save(final T entity) {
		getJPDao().saveAndFlush(entity);
	}

	
	public void delete(final T entity) {
		getJPDao().delete(entity);
	}

	
	public void deleteById(long entityId) {
		getJPDao().delete(entityId);
	}
	
	public Page<T> findAllByPage(Page<T> page){  
        Direction order = Sort.Direction.fromStringOrNull(page.getOrderDirection());  
        if (order==null){
        	order= Sort.DEFAULT_DIRECTION;
        }
 
        Sort sort = new Sort(order,page.getOrderField());
        PageRequest pr = new PageRequest(page.getPageNum()-1,page.getPageSize(),sort) ;
        org.springframework.data.domain.Page<T> pages = getJPDao().findAll(pr);
        page.setTotalCount(pages.getTotalElements());
        page.setTotalPage(pages.getTotalPages());
		page.setItems(pages.getContent()); 
        
        return page;  

	} 
	

	@SuppressWarnings("unchecked")
	public List<T> findWithParameters(Class<T> entityClass, String string) {
		String entityName = JPAUtil.getEntityName(entityClass);
		Query query = em.createQuery("select o from " +entityName+ " o where " + string);
		List<T> list = query.getResultList();  
		return list;
	}
	
	public Page<T> findPageWithParameters(Page<T> page) {
        Direction order = Sort.Direction.fromStringOrNull(page.getOrderDirection());  
        if (order==null){
        	order= Sort.DEFAULT_DIRECTION;
        }
 
        Sort sort = new Sort(order,page.getOrderField());
        PageRequest pr = new PageRequest(page.getPageNum()-1,page.getPageSize(),sort) ;
        org.springframework.data.domain.Page<T> pages = getJSEDao().findAll(page.getParameters(),pr);
        page.setTotalCount(pages.getTotalElements());
        page.setTotalPage(pages.getTotalPages());
		page.setItems(pages.getContent()); 
        return page;  
	}
	

}