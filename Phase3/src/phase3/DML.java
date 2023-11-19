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
	Menu ME;
	Menu_item IT;
	Ingredient IN;
	Need NE;
	Managed_item MI;
	Contains CO;
	Edited_menu EM;
	Login LO;
	String id = "";
	PreparedStatement ps;
	public DML(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
		this.CU = new Customer(conn, sc);
		this.MA = new Manager(conn, sc);
		this.CH = new Chef(conn, sc);
		this.ME = new Menu(conn, sc);
		this.IT = new Menu_item(conn, sc);
		this.IN = new Ingredient(conn, sc);
		this.NE = new Need(conn, sc);
		this.MI = new Managed_item(conn, sc);
		this.CO = new Contains(conn, sc);
		this.EM = new Edited_menu(conn, sc);
		this.LO = new Login(conn, sc);
	}
	
	public void Run() throws IOException {
		System.out.println("1. Customer");
		System.out.println("2. Manager");
		System.out.println("3. Chef");
		System.out.println("4. Menu");
		System.out.println("5. Menu_item");
		System.out.println("6. Ingredient");
		System.out.println("7. Login");
		System.out.print("Select Entity or Query: ");
		int Entity = sc.nextInt();
		System.out.println();
		
		int DML_type = 0;
		if (Entity != 7) {
			System.out.println("1. INSERT");
			System.out.println("2. UPDATE");
			System.out.println("3. DELETE");
			System.out.print("Select DML type: ");
			DML_type = sc.nextInt();
		}
		
		
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
			break;
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
			break;
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
			break;
		case 4: // Menu
			switch (DML_type) {
			case 1:
				id = ME.INSERT();
				CO.getMenu_id(id);
				EM.getMenu_id(id);
				while (true) {
					System.out.println("Menu에 넣을 Menu_item이 있습니까?: (1: 예, 2: 아니오)");
					if (sc.nextInt() == 1)
						CO.INSERT();
					else
						break;
				}
				EM.INSERT();
				break;
			case 2:
				CH.UPDATE();
				break;
			case 3:
				System.out.println("DELETE 불가");
			}
			break;
		case 5: // Menu_item
			switch (DML_type) {
			case 1:
				id = IT.INSERT();
				NE.getItem_id(id);
				MI.getItem_id(id);
				while (true) {
					System.out.println("Menu_item에 넣을 Ingredient가 있습니까?: (1: 예, 2: 아니오)");
					if (sc.nextInt() == 1)
						NE.INSERT();
					else
						break;
				}
				MI.INSERT();
				break;
			case 2:
				CH.UPDATE();
				break;
			case 3:
				System.out.println("DELETE 불가");
			}
			break;
		case 6: // Ingredient
			switch (DML_type) {
			case 1:
				System.out.println("INSERT 불가");
				break;
			case 2:
				CH.UPDATE();
				break;
			case 3:
				System.out.println("DELETE 불가");
			}
			break;
		case 7:
			LO.Run();
			break;
		}
	}
}
