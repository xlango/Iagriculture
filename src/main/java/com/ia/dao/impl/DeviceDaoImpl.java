package com.ia.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ia.dao.IDeviceDao;
import com.ia.entity.Data;
import com.ia.entity.Device;

/**
 * 持久层实现类
 * @author xyl
 *
 */
@Repository
public class DeviceDaoImpl implements IDeviceDao {

	@Resource
	private SessionFactory sessionFactory;	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(Device device) {
        this.getSession().save(device);
	}

	public void update(Device device) {
        this.getSession().update(device);
	}

	public void deletebyId(int id) {
        this.getSession().createQuery("delete Device where id=?")
            .setParameter(0, id).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Device> gets() {		
		return (List<Device>) this.getSession().createCriteria(Device.class).list();
	}

	public Device getbyId(int id) {
		return (Device)this.getSession().createQuery("from Device where id=?")
			       .setParameter(0, id).uniqueResult();
	}

	public Device getbyNum(String num,int farmid) {
		String hql = "from Device as d where  d.devNum="+num+" and d.farmId="+farmid; 
		return (Device) this.getSession().createQuery(hql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Device> getbyfarm(int farmid) {
		String hql = "from Device as d where  d.farmId="+farmid; 
		return (List<Device>) this.getSession().createQuery(hql).list();
	}



}
