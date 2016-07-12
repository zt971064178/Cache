package cn.itcast.cache.controller;

import java.util.List;

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
	@RequestMapping("addBaseUser")
	@ResponseBody
    public BaseUser addBaseUser(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = new BaseUser() ;
		// 缓存10条数据，id自增长
		for(int i = 1; i <= 10 ;i++) {
			baseUser.setUserId(i+"");
			baseUser.setUsername("张田");
			baseUser.setPassword("123456");
			baseUser.setAddress("苏州工业园区金鸡湖大道1355号");
			baseUser = baseUserService.addBaseUser(baseUser) ;
		}
		
        return baseUser ;
    }
	
	@RequestMapping("getUserById")
	@ResponseBody
	public BaseUser getUserById(HttpServletRequest request, HttpServletResponse response) {
		String id = "3" ;
		return baseUserService.getUserById(id) ;
	}
	
	/*
	 * 条件查询用户信息  缓存数据
	 */
	@RequestMapping("getUserList")
	@ResponseBody
	public List<BaseUser> getUserList(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = new BaseUser() ;
		// 查询时查询条件需要固定，如id不能为UUID，这样缓存永远无法命中，并且缓存中数据是累增长的
		String id = "zhangtian_caobeibei_demo2" ;
		baseUser.setUserId(id);
		baseUser.setUsername("张田");
		baseUser.setPassword("123456");
		baseUser.setAddress("苏州工业园区金鸡湖大道1355号");
		
		return baseUserService.getUserList(baseUser) ;
	}
	
	@RequestMapping("getUserById2")
	@ResponseBody
	public BaseUser getUserById2(HttpServletRequest request, HttpServletResponse response) {
		String id = "3" ;
		return baseUserService.getUserById2(id) ;
	}
}
