package cn.itcast.cache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.cache.dao.BaseUserDao;
import cn.itcast.cache.domain.BaseUser;

@Service
public class BaseUserServiceImpl implements BaseUserService {

	@Autowired
	private BaseUserDao baseUserDao ;
	
	@Override
	public BaseUser addBaseUser(BaseUser baseUser) {
		return baseUserDao.addUser(baseUser);
	}

	@Override
	public List<BaseUser> getUserList(BaseUser baseUser) {
		return baseUserDao.getUserList(baseUser);
	}

	@Override
	public BaseUser getUserById(String id) {
		return baseUserDao.getUserById(id);
	}

	@Override
	public BaseUser getUserById2(String id) {
		return baseUserDao.getUserById2(id);
	}

	// 清楚所有缓存
	@Override
	public void clearUserCache() {
		baseUserDao.clearUserCache();
	}

	// 更新用户名  刷新缓存
	@Override
	public void updateUserName(BaseUser baseUser) {
		baseUserDao.updateUserName(baseUser);
	}

	// 清除指定ID的用户的缓存
	@Override
	public void clearUserById2Cache(String id) {
		baseUserDao.clearUserById2Cache(id);
	}
}
