package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class DML {
	Connection conn;
	Scanner sc;
	String sql = "";
	Customer CU;
	Manager MA;
	Chef CH;
	PreparedStatement ps;
	public DML(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
		this.CU = new Customer(conn, sc);
		this.MA = new Manager(conn, sc);
		this.CH = new Chef(conn, sc);
	}
	
	public void Run() throws IOException {
		System.out.println("");
		
		System.out.println("1. Customer");
		System.out.println("2. Manager");
		System.out.println("3. Chef");
		System.out.print("Select Entity: ");
		int Entity = sc.nextInt();
		
		System.out.println("1. INSERT");
		System.out.println("2. UPDATE");
		System.out.println("3. DELETE");
		System.out.print("Select DML type:");
		int DML_type = sc.nextInt();
		
		switch (Entity) {
		case 1:
			switch (DML_type) {
			case 1:
				CU.INSERT();
				break;
			case 2:
				CU.UPDATE();
				break;
			case 3:
				CU.DELETE();
			}
		case 2:
			switch (DML_type) {
			case 1:
				MA.INSERT();
				break;
			case 2:
				MA.UPDATE();
				break;
			case 3:
				MA.DELETE();
			}
		case 3:
			switch (DML_type) {
			case 1:
				CH.INSERT();
				break;
			case 2:
				CH.UPDATE();
				break;
			case 3:
				CH.DELETE();
			}
		}
		
	}
}
