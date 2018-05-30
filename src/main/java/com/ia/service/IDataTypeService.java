package com.ia.service;

import java.util.List;

import com.ia.entity.DataType;
import com.ia.entity.Farm;

/**
 * 业务层接口
 * @author xyl
 *
 */
public interface IDataTypeService {

	public void add(DataType dataType);

	public void update(DataType dataType);

	public void deletebyId(int id);
	
	public List<DataType> gets();

	public DataType getbyName(String typeName);

}
