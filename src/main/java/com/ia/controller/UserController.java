package com.ia.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.User;
import com.ia.service.IUserService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/user")
@Api(value="用户接口",tags={"用户Api"})
public class UserController {
	User user = new User();
	@Resource
	private IUserService userService;

	@ResponseBody
	@RequestMapping(value = "/dologin")
	@ApiOperation(value = "登录", httpMethod = "POST", notes = "使用手机号和密码登录")
	public Result dologin(String phone,String pwd) {
		System.out.println(phone + "=====" + pwd);
		user = userService.getbyPhone(phone);
		if (user != null) {
			if (user.getPwd().equals(pwd)) {
				return ResultUtil.success();
			} else {
				return ResultUtil.error();
			}
		} else {
			return ResultUtil.error();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getuser")
	@ApiOperation(value = "获取用户信息", httpMethod = "GET", notes = "显示用户信息，不显示密码") 
	public Result getuser(String phone) {
		System.out.println("++++++" + phone);
		user = userService.getbyPhone(phone);
		if (user != null) {
			return ResultUtil.success();
		} else {
			return ResultUtil.error();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/registe", method = RequestMethod.POST)
	@ApiOperation(value = "注冊", httpMethod = "POST", notes = "使用手机号注冊")
	public Result registe(User user) {
		System.out.println("请求注册" + user.getAnswer());
		User user1 = userService.getbyPhone(user.getPhone());
		System.out.println(user);
		if (user1 != null) {
			System.out.println("用户已存在");
			return ResultUtil.error();
		} else {
			userService.add(user);
			return ResultUtil.success(user);
		}
	}

}
