package com.ia.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ia.dao.IUserDao;
import com.ia.entity.User;

/**
 * 持久层实现类
 * @author xyl
 *
 */
@Repository
public class UserDaoImpl implements IUserDao {

	@Resource
	private SessionFactory sessionFactory;	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void add(User user) {
        this.getSession().save(user);
	}

	public void update(User user) {
        this.getSession().update(user);
	}

	public void deletebyId(String id) {
        this.getSession().createQuery("delete User where id=?")
            .setParameter(0, id).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> gets() {		
		return (List<User>) this.getSession().createCriteria(User.class).list();
	}

	public User getbyId(String id) {
		return (User)this.getSession().createQuery("from User where id=?")
			       .setParameter(0, id).uniqueResult();
	}

	public User getbyPhone(String phone) {
		return (User)this.getSession().createQuery("from User where phone=?")
				.setParameter(0, phone).uniqueResult();
	}

}
