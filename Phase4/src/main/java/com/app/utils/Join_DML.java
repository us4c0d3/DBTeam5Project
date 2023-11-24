package com.app.utils;

import java.io.IOException;

import com.app.tables.TableMain;

public class Join_DML {
	TableMain tm;

	public Join_DML() {
		tm = new TableMain();
	}

	public String Customer_insert(String name, String password, String phone_number) throws IOException {
		String id = tm.customer.INSERT(name, password, phone_number);

		return id;
	}

	public String Chef_insert(String name, String phone_number) throws IOException {
		String id = tm.chef.INSERT(name, phone_number);

		return id;
	}

	public String Manager_insert(String name, String password, String phone_number) throws IOException {
		String id = tm.manager.INSERT(name, password, phone_number);

		return id;
	}

}
