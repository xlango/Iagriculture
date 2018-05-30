package com.ia.service;

import java.util.List;

import com.ia.entity.Farm;
import com.ia.entity.User;

/**
 * 业务层接口
 * @author xyl
 *
 */
public interface IFarmService {

	public void add(Farm farm);

	public void update(Farm farm);

	public void deletebyId(int id);

	public List<Farm> gets();

	public Farm getbyId(int id);

	public Farm getbyNum(String farmNum);
}
