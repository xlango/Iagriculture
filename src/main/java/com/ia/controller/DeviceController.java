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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/device")
@Api(value="设备接口",tags={"设备Api"})
public class DeviceController {

	Device data = new Device();
	@Resource
	private IDeviceService deviceService;
	@Resource
	private IFarmService farmService;

	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有设备", httpMethod = "GET", notes = "获取所有设备") 
	public Result gets() {
		List<Device> list = deviceService.gets();
		return ResultUtil.success(list);
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "添加设备", httpMethod = "POST", notes = "添加设备")
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
	@ApiOperation(value = "根据农场号获取农场设备", httpMethod = "POST", notes = "根据农场号获取农场设备")
	public Result getbyfarm(String farmNum) {
		System.out.println("farm:" + farmService.getbyNum(farmNum).getId());
		return ResultUtil.success(deviceService.getbyfarm(farmService.getbyNum(farmNum).getId()));
	}

	@ResponseBody
	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	@ApiOperation(value = "开关设备", httpMethod = "POST", notes = "开关设备")
	public Result switchs(String farmNum, String devNum,String devtype, Boolean sw) {
		System.out.println("farm:"+farmNum+"dev:"+devNum);
		Boolean sendflag = false;
		Device device= deviceService.getbyNum(devNum, farmService.getbyNum(farmNum).getId());
		if (sw) {
			sendflag = ZhnyServer.writeOrder(farmNum, "EF " + farmNum + " " + devtype  + " " + devNum + " 80 00 FF");
			device.setDevstate(1);
		} else {
			sendflag = ZhnyServer.writeOrder(farmNum, "EF " + farmNum + " " + devtype  + " " + devNum + " 81 00 FF");
			device.setDevstate(0);
		}
		if (sendflag) {						
			deviceService.update(device);
			return ResultUtil.success();
		} else {
			return ResultUtil.error();
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/state", method = RequestMethod.POST)
	@ApiOperation(value = "检测设备状态", httpMethod = "POST", notes = "检测设备状态")
	public Result state(String farmNum, String devNum,String devtype) {
		System.out.println("farm:"+farmNum+"dev:"+devNum+"devtype:"+devtype);
		ZhnyServer.writeOrder(farmNum, "EF " + farmNum + " " + devtype  + " " + devNum + " 82 00 FF");
		return ResultUtil.success(deviceService.getbyNum(devNum, farmService.getbyNum(farmNum).getId()));
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(value = "根据id删除设备", httpMethod = "POST", notes = "根据id删除设备")
	public Result delete(int id) {	
		deviceService.deletebyId(id);
		return ResultUtil.success();					
	}
    

	
}
