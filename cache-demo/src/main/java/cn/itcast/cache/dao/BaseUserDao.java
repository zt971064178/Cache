package cn.itcast.cache.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.type.CacheOpType;

import cn.itcast.cache.domain.BaseUser;

@Repository
public class BaseUserDao {
	private static final String cacheName = "user" ;
	private static final int expire = 121000 ;
	
	/**
	 *  addUser:(添加用户的同时，把数据放到缓存中). 
	 *  将Id并入到缓存间值中
	 *  @return_type:BaseUser
	 *  @author zhangtian  
	 *  @param userName
	 *  @return
	 */
	@Cache(expire=expire, key="'" + cacheName + "'+#retVal.userId", opType=CacheOpType.WRITE)
	public BaseUser addUser(BaseUser baseUser) {
		System.out.println("add User:"+baseUser);
		return baseUser ;
	}
	
	/**
     * 使用自定义缓存Key，并在指定的条件下才进行缓存。
     * 查询缓存
     * 根据缓存键值key查询缓存，未命中则查询数据库并写入缓存
     * @param id
     * @return
     */
    @Cache(expire=expire, autoload=true, key="'"+ cacheName +"'+#args[0]"/*, condition="#args[0]>0"*/)
    public BaseUser getUserById(String id) {
    	BaseUser user = new BaseUser();
        user.setUserId("11");;
        user.setUsername("曹贝贝");
        user.setPassword("dfghjkl");
        user.setAddress("苏州创意产业园");
        System.out.println("getUserById from dao");
        return user;
    }
    
    /**
     * 使用 hash 方法，将参数转为字符串
     * @param user
     * @return
     */
    @Cache(expire=expire, key="'" + cacheName + "'+#hash(#args)")
    public List<BaseUser> getUserList(BaseUser user) {
        List<BaseUser> list=new ArrayList<BaseUser>();
        
        BaseUser baseUser = new BaseUser() ;
        baseUser.setUserId("cao_security");
        baseUser.setUsername("密码"); 

        list.add(baseUser) ;
        list.add(user);
        System.out.println("getUserList from dao");
        return list;
    }
    
    /**
     * 使用自定义缓存Key，并在指定的条件下才进行缓存。
     * 匹配上一个方法，获取缓存数据
     * @param id
     * @return
     */
    @Cache(expire=expire, autoload=false, key="'"+ cacheName +"'+#args[0]"/*, condition="#args[0]>0"*/)
    public BaseUser getUserById2(String id) {
        Thread thread=Thread.currentThread();
        System.out.println("thread:" + thread.getName() + ";getUserById2");
        try {
            // 模拟阻塞
            Thread.sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        BaseUser user=new BaseUser();
        user.setUserId(id);
        user.setUsername("忠实的");
        user.setPassword("34567890");
        user.setAddress("测试地址");
        // throw new Exception("test");// 异常情况测试
        return user;
    }
	
}
