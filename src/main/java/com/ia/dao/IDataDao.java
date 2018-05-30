package com.ia.dao;

import java.util.List;

import com.ia.entity.Data;


/**
 * 持久层接口
 * @author xyl
 *
 */
public interface IDataDao {

	public void add(Data data);

	public void update(Data data);

	public void deletebyId(String id);

	public List<Data> gets();

	public Data getbyId(String id);

	public Data getbyName(String name);
	
	public List<Data> getbyTime(String farmNum,String time,int typeId);
}
