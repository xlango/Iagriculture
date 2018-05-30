package com.ia.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ia.dao.IDeviceDao;
import com.ia.entity.Device;
import com.ia.service.IDeviceService;

/**
 * 业务层
 * @author xyl
 *
 */
@Transactional
@Service
public class DeviceServiceImpl implements IDeviceService {

	@Autowired
	private IDeviceDao deviceDao;
	
	public void add(Device device) {
		deviceDao.add(device);
	}

	public void update(Device device) {
		deviceDao.update(device);
	}

	public void deletebyId(int id) {
		deviceDao.deletebyId(id);

	}

	public List<Device> gets() {
		return deviceDao.gets();
	}

	public Device getbyId(int id) {
		return deviceDao.getbyId(id);
	}

	public Device getbyNum(String num,int farmid) {
		return deviceDao.getbyNum(num,farmid);
	}

	public List<Device> getbyfarm(int farmid) {
		return deviceDao.getbyfarm(farmid);
	}

}
