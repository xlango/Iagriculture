package com.ia.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.dao.IDataTypeDao;
import com.ia.entity.DataType;
import com.ia.service.IDataTypeService;


/**
 * 业务层
 * @author xyl
 *
 */
@Transactional
@Service
public class DataTypeServiceImpl implements IDataTypeService{

	@Autowired
	private IDataTypeDao dataTypeDao;
	

	public List<DataType> gets() {		
		return dataTypeDao.gets();
	}


	public void add(DataType dataType) {
		dataTypeDao.add(dataType);		
	}


	public void update(DataType dataType) {
		dataTypeDao.update(dataType);
	}


	public void deletebyId(int id) {
		dataTypeDao.deletebyId(id);
	}


	public DataType getbyName(String typeName) {
		return dataTypeDao.getbyName(typeName);
	}

	

}
