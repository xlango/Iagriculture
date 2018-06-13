package com.ia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.Data;
import com.ia.service.IDataService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value = "/data")
@Api(value="数据接口",tags={"数据Api"})
public class DataController {

	Data data=new Data();
	@Resource
	private IDataService dataService;
		
	@ResponseBody
	@RequestMapping(value = "/time", method = RequestMethod.POST)
	@ApiOperation(value = "根据时间查询数据", httpMethod = "POST", notes = "根据时间查询数据")
	public Result getbyTime(String farmNum,String time,int typeId) {
		System.out.println("farm:"+farmNum+",time:"+time+",typeId:"+typeId);
		List<Data> list=dataService.getbyTime(farmNum, time,typeId);
             	
		return ResultUtil.success(list);		
	}
		
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有数据", httpMethod = "GET", notes = "获取所有数据") 
	public Result gets() {	
		List<Data> list=dataService.gets();
			return ResultUtil.success(list);		
	}
}
