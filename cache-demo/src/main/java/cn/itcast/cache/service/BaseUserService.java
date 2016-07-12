package cn.itcast.cache.service;

import java.util.List;

import cn.itcast.cache.domain.BaseUser;

public interface BaseUserService {
	public BaseUser addBaseUser(BaseUser baseUser) ;
	
	public List<BaseUser> getUserList(BaseUser baseUser) ;
	
	public BaseUser getUserById(String id) ;
	
	public BaseUser getUserById2(String id) ;
}
