package com.ia.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ia.dao.IFarmDao;
import com.ia.entity.Farm;

/**
 * 持久层实现类
 * @author xyl
 *
 */
@Repository
public class FarmDaoImpl implements IFarmDao{

	@Resource
	private SessionFactory sessionFactory;	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(Farm farm) {
        this.getSession().save(farm);
	}

	public void update(Farm farm) {
        this.getSession().update(farm);
	}

	public void deletebyId(int id) {
        this.getSession().createQuery("delete Farm where id=?")
            .setParameter(0, id).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Farm> gets() {		
		return (List<Farm>) this.getSession().createCriteria(Farm.class).list();
	}

	public Farm getbyId(int id) {
		return (Farm)this.getSession().createQuery("from Farm where id=?")
			       .setParameter(0, id).uniqueResult();
	}

	public Farm getbyNum(String farmNum) {
		return (Farm)this.getSession().createQuery("from Farm where farmNum=?")
			       .setParameter(0, farmNum).uniqueResult();
	}




}
