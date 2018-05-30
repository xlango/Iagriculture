package com.ia.dao;

import java.util.List;

import com.ia.entity.Farm;

/**
 * 持久层接口
 * @author xyl
 *
 */
public interface IFarmDao {

	public void add(Farm farm);

	public void update(Farm farm);

	public void deletebyId(int id);

	public List<Farm> gets();

	public Farm getbyId(int id);

	public Farm getbyNum(String farmNum);
}
