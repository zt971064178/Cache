package cn.itcast.cache.controller;

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
	
	@RequestMapping("/addBaseUser")
	@ResponseBody
    public BaseUser addBaseUser(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = new BaseUser() ;
		baseUser.setUserId(UUID.randomUUID().toString());
		baseUser.setUsername("张田");
		baseUser.setPassword("123456");
		baseUser.setAddress("苏州工业园区金鸡湖大道1355号");
		
		baseUserService.addBaseUser(baseUser) ;
        return baseUser ;
    }
}
