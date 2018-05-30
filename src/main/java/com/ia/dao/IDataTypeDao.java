package com.ia.dao;

import java.util.List;

import com.ia.entity.DataType;
import com.ia.entity.Farm;


/**
 * 持久层接口
 * @author xyl
 *
 */
public interface IDataTypeDao {
	public void add(DataType dataType);

	public void update(DataType dataType);

	public void deletebyId(int id);
	
	public List<DataType> gets();

	public DataType getbyName(String typeName);
}
