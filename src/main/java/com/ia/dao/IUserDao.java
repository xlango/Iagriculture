package com.ia.dao;

import java.util.List;

import com.ia.entity.User;

/**
 * 持久层接口
 * @author xyl
 *
 */
public interface IUserDao {

	public void add(User user);

	public void update(User user);

	public void deletebyId(String id);

	public List<User> gets();

	public User getbyId(String id);

	public User getbyPhone(String Phone);
}
