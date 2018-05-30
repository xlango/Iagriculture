package com.ia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.Device;
import com.ia.entity.Farm;
import com.ia.server.ZhnyServer;
import com.ia.service.IDeviceService;
import com.ia.service.IFarmService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {

	Device data = new Device();
	@Resource
	private IDeviceService deviceService;
	@Resource
	private IFarmService farmService;

	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result gets() {
		List<Device> list = deviceService.gets();
		return ResultUtil.success(list);
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result add(Device d) {
		if (deviceService.getbyNum(d.getDevNum(), d.getFarmId()) == null) {
			deviceService.add(d);
			return ResultUtil.success();
		} else {
			return ResultUtil.error();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/farmid", method = RequestMethod.POST)
	public Result getbyfarm(String farmNum) {
		System.out.println("farm:" + farmService.getbyNum(farmNum).getId());
		return ResultUtil.success(deviceService.getbyfarm(farmService.getbyNum(farmNum).getId()));
	}

	@ResponseBody
	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	public Result switchs(String farmNum, String devNum, Boolean sw) {
		System.out.println("farm:"+farmNum+"dev:"+devNum);
		Boolean sendflag = false;
		if (sw) {
			sendflag = ZhnyServer.writeOrder(farmNum, "EF " + farmNum + " " + devNum + " 01 FF");
		} else {
			sendflag = ZhnyServer.writeOrder(farmNum, "EF " + farmNum + " " + devNum + " 00 FF");
		}
		if (sendflag) {
			return ResultUtil.success();
		} else {
			return ResultUtil.error();
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public Result state() {
		//System.out.println("farm:"+farmNum+"dev:"+devNum);
		String reState = null;
		reState = ZhnyServer.devState("11", "EF DD FF");
		if (reState!=null) {
			return ResultUtil.success(reState);
		} else {
			return ResultUtil.error();
		}
	}
}
