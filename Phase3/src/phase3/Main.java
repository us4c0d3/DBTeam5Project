package phase3;

import java.io.*;
import java.sql.*; // import JDBC package

public class Main {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_NAME ="teamproject"; 
	public static final String USER_PASSWD ="comp322";
		
	public static void main(String[] args) throws IOException {
		Connection conn = null; // Connection object
		Statement stmt = null;	// Statement object
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
			stmt = conn.createStatement();
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Query Q = new Query(conn, br);
        String line = "";
        
		while (true) {
			System.out.println("");
			System.out.println("0. Exit");
			System.out.println("1. Query");
			System.out.println("2. DML");
			System.out.println("Select Type: ");
            line = br.readLine();
            int requirement = Integer.parseInt(line);
            
            if (requirement == 1) { // Query
    			System.out.println("");
    			System.out.println("Query: 2, 3, 4, 6, 7, 9, 18, 20");
    			System.out.println("Select Query Type: ");
            	line = br.readLine();
                int query_number = Integer.parseInt(line);
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
                case 18:
                	Q.Q18();
                	break;
                case 20:
                	Q.Q20();
                	break;
                }
            }
            else if (requirement == 2) { // DML
    			System.out.println("");
            	
            }
            else if (requirement == 0) { // EXIT
    			System.out.println("");
            	System.out.println("Exit");
            	break;
            }
		}
		
		// Release database resources.
		try {
			// Close the Statement object.
			stmt.close(); 
			// Close the Connection object.
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}