package com.ia.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.User;
import com.ia.service.IUserService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	User user=new User();
	@Resource
	private IUserService userService;
	
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public Result login() {	
		System.out.println("请求登录==========");
		
		return ResultUtil.success();
	}
	
	@ResponseBody
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public Result dologin(String phone,String pwd) {
		System.out.println(phone+"====="+pwd);	
		user=userService.getbyPhone(phone);
		if (user!=null) {
			if(user.getPwd().equals(pwd)) {			
				return ResultUtil.success();
			}else {
				return ResultUtil.error();
			}			
		}
		else {
			return ResultUtil.error();
		}		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public Result getuser(String phone) {
		System.out.println("++++++"+phone);	
		user=userService.getbyPhone(phone);
		if (user!=null) {		
				return ResultUtil.success();		
		}
		else {
			return ResultUtil.error();
		}		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/registe", method = RequestMethod.POST)
	public Result registe(User user) {
		System.out.println("请求注册"+user.getAnswer());	
		User user1=userService.getbyPhone(user.getPhone());	
		System.out.println(user);
		if (user1 != null) {	
			System.out.println("用户已存在");
			return ResultUtil.error();
		}
		else {			
			userService.add(user);			
			return ResultUtil.success(user);		
		}	
	}
	
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Result test() {
		
		user.setName("123");
		user.setPhone("123");
		user.setAnswer("123");
		user.setPwd("123");
		user.setQuestion("123");
		user.setSex("123");
		userService.add(user);
			
	  return ResultUtil.success(user);		
		
	}
}
