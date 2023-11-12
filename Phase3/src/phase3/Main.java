package phase3;

import java.util.*;
import java.io.*;
import java.sql.*; // import JDBC package

public class Main {
	public static final String URL = "jdbc:oracle:thin:@192.168.219.100:1521/orcl";
	public static final String USER_NAME ="teamproject"; 
	public static final String USER_PASSWD ="comp322";
		
	public static void main(String[] args) throws IOException {
		Connection conn = null; // Connection object
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object 
			System.out.println("Success!");
		}catch(ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try{
			conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD); 
			System.out.println("Connected.");
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		System.out.println();
		Scanner sc = new Scanner(System.in);
		
		Query Q = new Query(conn, sc);
		DML D = new DML(conn, sc);
        
		while (true) {
			System.out.println("0. Exit");
			System.out.println("1. Query");
			System.out.println("2. DML");
			System.out.print("Select Type: ");
            int requirement = sc.nextInt();
            System.out.println();
            if (requirement == 1) { // Query
    			System.out.println("Query: 2, 3, 4, 6, 7, 9, 12, 13, 17, 18, 19, 20");
    			System.out.print("Select Query Type: ");
                int query_number = sc.nextInt();
                switch (query_number) {
                case 2:
                	Q.Q2();
                	break;
                case 3:
                	Q.Q3();
                	break;
                case 4:
                	Q.Q4();
                	break;
                case 6:
                	Q.Q6();
                	break;
                case 7:
                	Q.Q7();
                	break;
                case 9:
                	Q.Q9();
                	break;
                case 12:
                	Q.Q12();
                	break;
                case 13:
                	Q.Q13();
                	break;
                case 17:
                	Q.Q17();
                	break;
                case 18:
                	Q.Q18();
                	break;
                case 19:
                	Q.Q19();
                case 20:
                	Q.Q20();
                	break;
                }
            }
            else if (requirement == 2) { // DML
            	D.Run();
            }
            else if (requirement == 0) { // EXIT
            	System.out.println("Exit");
            	break;
            }
		}
		
		sc.close();
		
		// Release database resources.
		try {
			// Close the Connection object.
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}