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
}
