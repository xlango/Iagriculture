package com.ia.service;

import java.util.List;

import com.ia.entity.Data;
import com.ia.entity.Device;


/**
 * 持久层接口
 * @author xyl
 *
 */
public interface IDeviceService {

	public void add(Device device);

	public void update(Device device);

	public void deletebyId(int id);

	public List<Device> gets();

	public Device getbyId(int id);
	
	public Device getbyNum(String num,int farmid);	
	
	public List<Device> getbyfarm(int farmid);
}
