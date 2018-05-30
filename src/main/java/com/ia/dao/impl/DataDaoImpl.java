package com.ia.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ia.dao.IDataDao;
import com.ia.entity.Data;

/**
 * 持久层实现类
 * @author xyl
 *
 */
@Repository
public class DataDaoImpl implements IDataDao {

	@Resource
	private SessionFactory sessionFactory;	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(Data data) {
        this.getSession().save(data);
	}

	public void update(Data data) {
        this.getSession().update(data);
	}

	public void deletebyId(String id) {
        this.getSession().createQuery("delete Data where id=?")
            .setParameter(0, id).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Data> gets() {		
		return (List<Data>) this.getSession().createCriteria(Data.class).list();
	}

	public Data getbyId(String id) {
		return (Data)this.getSession().createQuery("from Data where id=?")
			       .setParameter(0, id).uniqueResult();
	}

	public Data getbyName(String name) {
		return (Data)this.getSession().createQuery("from Data where name=?")
				.setParameter(0, name).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Data> getbyTime(String farmNum, String time,int typeId) {
		String hql = "from Data as d where d.farmNum = '"+farmNum+"' and d.typeId="+typeId+" and d.createTime like '%"+time+"%'";   
		//Query query = session.createQuery(hql);   
		return (List<Data>) this.getSession().createQuery(hql).list();
	}

}
