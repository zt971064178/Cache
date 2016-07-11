package cn.itcast.cache.dao;

import org.springframework.stereotype.Repository;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.type.CacheOpType;

import cn.itcast.cache.domain.BaseUser;

@Repository
public class BaseUserDao {
	private static final String cacheName = "user" ;
	private static final int expire = 210000 ;
	
	/**
	 *  addUser:(添加用户的同时，把数据放到缓存中). 
	 *  @return_type:BaseUser
	 *  @author zhangtian  
	 *  @param userName
	 *  @return
	 */
	@Cache(expire=expire, key="'"+cacheName+"'+#retVal.userId", opType=CacheOpType.WRITE)
	public BaseUser addUser(BaseUser baseUser) {
		System.out.println("add User:"+baseUser);
		return baseUser ;
	}
	
}
