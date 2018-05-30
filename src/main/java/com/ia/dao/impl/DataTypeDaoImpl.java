package com.ia.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ia.dao.IDataTypeDao;
import com.ia.entity.DataType;
import com.ia.entity.Farm;


/**
 * 持久层实现类
 * @author xyl
 *
 */
@Repository
public class DataTypeDaoImpl implements IDataTypeDao {

	@Resource
	private SessionFactory sessionFactory;	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<DataType> gets() {
		return (List<DataType>) this.getSession().createCriteria(DataType.class).list();
	}

	public void add(DataType dataType) {
		this.getSession().save(dataType);
		
	}

	public void update(DataType dataType) {
		this.getSession().update(dataType);
		
	}

	public void deletebyId(int id) {
		  this.getSession().createQuery("delete DataType where id=?")
          .setParameter(0, id).executeUpdate();
	}

	public DataType getbyName(String typeName) {
		 return (DataType)this.getSession().createQuery("from DataType where typeName=?")
			       .setParameter(0, typeName).uniqueResult();
	}

	
}
