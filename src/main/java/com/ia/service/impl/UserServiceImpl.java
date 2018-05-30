package com.ia.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.dao.IUserDao;
import com.ia.entity.User;
import com.ia.service.IUserService;


/**
 * 业务层
 * @author xyl
 *
 */
@Transactional
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao;
	public void add(User user) {
		userDao.add(user);
	}

	public void update(User user) {
		userDao.update(user);
	}

	public void deletebyId(String id) {
		userDao.deletebyId(id);		
	}

	public List<User> gets() {		
		return userDao.gets();
	}

	public User getbyId(String id) {
		return userDao.getbyId(id);
	}

	public User getbyPhone(String phone) {
		return userDao.getbyPhone(phone);
	}

}
