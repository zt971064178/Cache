package cn.itcast.cache.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.cache.domain.BaseUser;
import cn.itcast.cache.service.BaseUserService;

@Controller
@RequestMapping("/")
public class BaseUserController {
	
	@Autowired
	private BaseUserService baseUserService ;
	
	/*
	 * 新增用户信息  缓存数据
	 */
	@RequestMapping("/addBaseUser")
	@ResponseBody
    public BaseUser addBaseUser(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = new BaseUser() ;
		baseUser.setUserId(UUID.randomUUID().toString());
		baseUser.setUsername("张田");
		baseUser.setPassword("123456");
		baseUser.setAddress("苏州工业园区金鸡湖大道1355号");
		
		baseUser = baseUserService.addBaseUser(baseUser) ;
        return baseUser ;
    }
	
	/*
	 * 条件查询用户信息  缓存数据
	 */
	@RequestMapping("/getUserList")
	@ResponseBody
	public List<BaseUser> getUserList(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = new BaseUser() ;
		String id = "zhangtian_caobeibei_demo" ;
		baseUser.setUserId(id);
		baseUser.setUsername("张田");
		baseUser.setPassword("123456");
		baseUser.setAddress("苏州工业园区金鸡湖大道1355号");
		
		return baseUserService.getUserList(baseUser) ;
	}
}
