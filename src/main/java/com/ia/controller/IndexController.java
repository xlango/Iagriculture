package com.ia.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ia.entity.User;
import com.ia.server.ZhnyServer;
import com.ia.service.IUserService;
import com.ia.utils.Result;
import com.ia.utils.ResultUtil;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
   
	User user=new User();
	@Resource
	private IUserService userService;
	
	
	@RequestMapping(value = "/initIndex")
	@ResponseBody
	//@PostConstruct 启动端口监听
	public void initIndex() {	
		//ZigbeeSocketServer.startServer(8020);
		ZhnyServer.startServer();
		System.out.println("启动服务器==========");		
		//return ResultUtil.success();
	}
	
	/**
	 * 主界面
	 */
	@RequestMapping(value = "/")
	public String index() {	
		return "index";
	}
	
	/**
	 * 注册界面
	 */
	@RequestMapping(value = "/register")
	public String register() {	
		return "register";
	}
	

	/**
	 * 注册界面
	 */
	@RequestMapping(value = "/login")
	public String login() {	
		return "login";
	}
	
	/**
	 * 设置阀值界面
	 */
	@RequestMapping(value = "/fazhi")
	public String fazhi() {	
		return "fazhi";
	}
	
	/**
	 * 液位界面
	 */
	@RequestMapping(value = "/yewei")
	public String yewei() {	
		return "yewei";
	}
	
	/**
	 * 光照界面
	 */
	@RequestMapping(value = "/light")
	public String light() {	
		return "light";
	}
	
	/**
	 * 空气温度界面
	 */
	@RequestMapping(value = "/kqwendu")
	public String kqwendu() {	
		return "kqwendu";
	}
	
	/**
	 * 空气湿度界面
	 */
	@RequestMapping(value = "/kqshidu")
	public String kqshidu() {	
		return "kqshidu";
	}
	
	/**
	 * 大气压界面
	 */
	@RequestMapping(value = "/daqiya")
	public String daqiya() {	
		return "daqiya";
	}
	
	/**
	 * 水分界面
	 */
	@RequestMapping(value = "/shuifen")
	public String shuifen() {	
		return "shuifen";
	}
	
	/**
	 * 土壤温度界面
	 */
	@RequestMapping(value = "/trwendu")
	public String trwendu() {	
		return "trwendu";
	}
	
	/**
	 * 雨水界面
	 */
	@RequestMapping(value = "/yushui")
	public String yushui() {	
		return "yushui";
	}
	
	/**
	 * 二氧化碳浓度界面
	 */
	@RequestMapping(value = "/co2")
	public String co2() {	
		return "co2";
	}
	
	/**
	 * 设备界面
	 */
	@RequestMapping(value = "/shebei")
	public String shebei() {	
		return "shebei";
	}
	
	/**
	 * 登录成功，跳转至主界面
	 * @return
	 */
	@RequestMapping(value = "/main")
	public String main() {	
		return "main";
	}
	
	/**
	 * 登录成功，跳转至主界面
	 * @return
	 */
	@RequestMapping(value = "/main2")
	public String main2() {	
		return "main2";
	}
	
	/**
	 * 接受设备号查询设备IP
	 * @return
	 */
	@RequestMapping(value = "/devnum")
	@ResponseBody
	public Result devnum(String devnum) {	
		System.out.println("设备号："+devnum);
		return ResultUtil.success();
	}
	
	
	@RequestMapping(value = "/sendMSG")
	@ResponseBody
	public Result sendMSG(String mac,String data) throws IOException {			
		ZhnyServer.writeOrder(mac, data);
        System.out.println("执行发送数据");
          return ResultUtil.success();
	}
	
}
