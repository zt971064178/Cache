package cn.itcast.commons.cache;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class SimpleCacheTest {
	@Test
	public void testSerizable() {
		JSONObject jsonObject = new JSONObject() ;
		jsonObject.put("username", "") ;
		jsonObject.put("password", null) ;
		jsonObject.put("age", 10) ;
		System.out.println(jsonObject.toJSONString());
		System.out.println(jsonObject.toString());
	}
}
