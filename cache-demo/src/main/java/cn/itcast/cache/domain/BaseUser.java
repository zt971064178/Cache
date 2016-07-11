package cn.itcast.cache.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@ToString
public @Data class BaseUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId ;
	private String username ;
	private String password ;
	private String address ;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
