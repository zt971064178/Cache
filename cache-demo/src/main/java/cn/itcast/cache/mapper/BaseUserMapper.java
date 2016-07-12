package cn.itcast.cache.mapper;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDelete;
import com.jarvis.cache.annotation.CacheDeleteKey;

import cn.itcast.cache.domain.BaseUser;

// 实战连接数据库的操作，mybatis
public interface BaseUserMapper {
	@Cache(expire=600, autoload=true, key="'user_mapper_getUserById_'+#args[0]", condition="#args[0]>0")
	BaseUser getUserById(Integer id);

    int addUser(BaseUser user);

    @CacheDelete({@CacheDeleteKey(value="'user_mapper_getUserById_'+#args[0].id")})
    int incAge(BaseUser user);
}
