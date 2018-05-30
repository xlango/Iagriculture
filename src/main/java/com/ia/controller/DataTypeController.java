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


@Controller
@RequestMapping(value = "/datatype")
public class DataTypeController {

	DataType datatype=new DataType();
	@Resource
	private IDataTypeService dataTypeService;
		
		
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result gets() {	
		List<DataType> list=dataTypeService.gets();
			return ResultUtil.success(list);		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
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
	public Result delete(int id) {	
		dataTypeService.deletebyId(id);
			return ResultUtil.success();					
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(DataType d) {	
		dataTypeService.update(d);
		return ResultUtil.success();					
	}
}
