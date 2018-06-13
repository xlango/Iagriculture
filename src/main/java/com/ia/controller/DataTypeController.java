package com.ia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.Data;
import com.ia.entity.DataType;
import com.ia.entity.Farm;
import com.ia.service.IDataTypeService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value = "/datatype")
@Api(value="数据类型接口",tags={"数据类型Api"})
public class DataTypeController {

	DataType datatype=new DataType();
	@Resource
	private IDataTypeService dataTypeService;
		
		
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有数据类型", httpMethod = "GET", notes = "获取所有数据类型") 
	public Result gets() {	
		List<DataType> list=dataTypeService.gets();
			return ResultUtil.success(list);		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(value = "添加数据类型", httpMethod = "POST", notes = "添加数据类型")
	public Result add(DataType d) {	
		if(dataTypeService.getbyName(d.getTypeName())==null) {
			dataTypeService.add(d);
			return ResultUtil.success();
		}else {
			return ResultUtil.error();
		}					
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(value = "根据id删除数据类型", httpMethod = "POST", notes = "根据id删除数据类型")
	public Result delete(int id) {	
		dataTypeService.deletebyId(id);
			return ResultUtil.success();					
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(value = "修改数据类型", httpMethod = "POST", notes = "修改数据类型")
	public Result update(DataType d) {	
		dataTypeService.update(d);
		return ResultUtil.success();					
	}
	
	@ResponseBody
	@RequestMapping(value = "/typename", method = RequestMethod.POST)
	@ApiOperation(value = "根据类型名成获取数据类型", httpMethod = "POST", notes = "根据类型名成获取数据类型")
	public Result getbyName(DataType d) {	
		return ResultUtil.success(dataTypeService.getbyName(d.getTypeName()));					
	}
}
