package com.ia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.Farm;
import com.ia.server.ZhnyServer;
import com.ia.service.IFarmService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value = "/farm")
@Api(value="农场接口",tags={"农场Api"})
public class FarmController {
	Farm farm=new Farm();
	@Resource
	private IFarmService farmService;
		

	@ResponseBody
	@RequestMapping(value = "/all")
	@ApiOperation(value = "获取所有农场", httpMethod = "GET", notes = "获取所有农场")  
	public Result gets() {	
		List<Farm> list=farmService.gets();
			return ResultUtil.success(list);		
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "添加农场", httpMethod = "POST", notes = "添加农场")
	public Result add(Farm f) {	
		if(farmService.getbyNum(f.getFarmnum())==null) {
			farmService.add(f);
			return ResultUtil.success();
		}else {
			return ResultUtil.error();
		}					
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(value = "根据id删除农场", httpMethod = "POST", notes = "根据id删除农场")
	public Result delete(int id) {	
		farmService.deletebyId(id);
			return ResultUtil.success();					
	}
	
	@ResponseBody
	@RequestMapping(value = "/model", method = RequestMethod.POST)
	@ApiOperation(value = "农场自动手动模式切换", httpMethod = "POST", notes = "农场自动手动模式切换")
	public Result model(int id) {	
		Farm f=farmService.getbyId(id);
		Boolean sendflag=false;
		if(f.getModel()==0) {
			f.setModel(1);
			sendflag=ZhnyServer.writeOrder(f.getFarmnum(), "EF 01 FF");
		}else {
			f.setModel(0);
			sendflag=ZhnyServer.writeOrder(f.getFarmnum(), "EF 00 FF");
		}
		
		if(sendflag) {
		farmService.update(f);
		return ResultUtil.success(f);	
		}else {
			return ResultUtil.error();	
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/state", method = RequestMethod.POST)
	@ApiOperation(value = "检测农场态", httpMethod = "POST", notes = "检测农场状态")
	public Result state(int id) {	
		Farm f=farmService.getbyId(id);
		Boolean sendflag=false;
			sendflag=ZhnyServer.writeOrder(f.getFarmnum(), "EF FA 01 FF");		
		if(sendflag) {
		    return ResultUtil.success(f);	
		}else {
			return ResultUtil.error();	
		}
	}
}
