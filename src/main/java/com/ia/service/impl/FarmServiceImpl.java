package com.ia.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.dao.IFarmDao;
import com.ia.entity.Farm;
import com.ia.entity.User;
import com.ia.service.IFarmService;


/**
 * 业务层
 * @author xyl
 *
 */
@Transactional
@Service
public class FarmServiceImpl implements IFarmService{

	@Autowired
	private IFarmDao farmDao;

	public void add(Farm farm) {
		farmDao.add(farm);
		
	}

	public void update(Farm farm) {
		farmDao.update(farm);
		
	}

	public void deletebyId(int id) {
		farmDao.deletebyId(id);
		
	}

	public List<Farm> gets() {	
		return farmDao.gets();
	}

	public Farm getbyId(int id) {
		return farmDao.getbyId(id);
	}

	public Farm getbyNum(String farmNum) {
		return farmDao.getbyNum(farmNum);
	}
	
}
