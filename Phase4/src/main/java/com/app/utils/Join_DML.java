package com.app.utils;

import java.io.IOException;

import com.app.tables.TableMain;

public class JoinDML {
	TableMain tm;

	public JoinDML() {
		tm = new TableMain();
	}

	public String CustomerInsert(String name, String password, String phone_number) throws IOException {
		String id = tm.customer.INSERT(name, password, phone_number);

		return id;
	}

	public String ChefInsert(String name, String phone_number) throws IOException {
		String id = tm.chef.INSERT(name, phone_number);

		return id;
	}

	public String ManagerInsert(String name, String password, String phone_number) throws IOException {
		String id = tm.manager.INSERT(name, password, phone_number);

		return id;
	}

}
