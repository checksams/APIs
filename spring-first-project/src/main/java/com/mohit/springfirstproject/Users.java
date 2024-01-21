package com.mohit.springfirstproject;

import java.util.ArrayList;
import java.util.List;


public class Users {

	private List<LoginForm> loginForm = new ArrayList<>();

	public List<LoginForm> getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(List<LoginForm> loginForm) {
		this.loginForm = loginForm;
	}
}
