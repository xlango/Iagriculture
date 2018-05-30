package com.ia.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.dao.IDataDao;
import com.ia.entity.Data;
import com.ia.service.IDataService;


/**
 * 业务层
 * @author xyl
 *
 */
@Transactional
@Service
public class DataServiceImpl implements IDataService{

	@Autowired
	private IDataDao dataDao;
	public void add(Data data) {
		System.out.println("service层:"+data.getData());
		dataDao.add(data);
	}

	public void update(Data data) {
		dataDao.update(data);
	}

	public void deletebyId(String id) {
		dataDao.deletebyId(id);		
	}

	public List<Data> gets() {		
		return dataDao.gets();
	}

	public Data getbyId(String id) {
		return dataDao.getbyId(id);
	}

	public Data getbyName(String name) {
		return dataDao.getbyName(name);
	}

	public List<Data> getbyTime(String farmNum, String time,int typeId) {
		return dataDao.getbyTime(farmNum, time,typeId);
	}

}
