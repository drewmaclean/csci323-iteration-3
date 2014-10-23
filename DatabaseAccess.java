import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseAccess {
	private static Connection conn;
	private static String table,
	keyField;
	
	// Load the MySQL JDBC driver
	private String driverName, 
	serverName, 
	schema, 
	url, 
	userName, 
	password;

    public DatabaseAccess() throws SQLException, ClassNotFoundException {
    	table = "AAPL";
    	keyField = "idr_table";
    	driverName = "org.gjt.mm.mysql.Driver";
    	serverName = "sql5.freemysqlhosting.net";
    	schema = "sql555866";
    	url = "jdbc:mysql://" + serverName +  "/" + schema;
    	userName = "sql555866";
    	password = "vL3*mR8%";

        Class.forName(driverName);
    }

    private void openConnection() throws SQLException {
        conn = DriverManager.getConnection(url, userName, password);
    }

    private void closeConnection() throws SQLException {
        conn.close();
    }

    
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		DatabaseAccess db = new DatabaseAccess();
		try{
			db.openConnection();
			Statement State = conn.createStatement();
			
			String str = String.format("SELECT * FROM " + table);
			State.executeQuery(str);
			ResultSet RS = State.getResultSet(); // database scanner
            ArrayList<String[]> rowAL = new ArrayList<String[]>();
            

            while (RS.next()) {
            	String[] rowArray = new String[6];
                rowArray[0] = (RS.getString(1));
                rowArray[1] = (RS.getString(2));
                rowArray[2] = (RS.getString(3));
                rowArray[3] = (RS.getString(4));
                rowArray[4] = (RS.getString(5));
                rowArray[5] = (RS.getString(6));
                rowAL.add(rowArray);
            }
            System.out.println("Done getting data");
            for (int i = 0; i < rowAL.size(); i++) {
            	
            	System.out.println(rowAL.get(i)[0]+"\t"+rowAL.get(i)[1]+"\t"+rowAL.get(i)[2]+"\t"+rowAL.get(i)[3]+"\t"+rowAL.get(i)[4]+"\t"+rowAL.get(i)[5]+"\n");
            }
		} catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        } finally {
        	db.closeConnection();
        }

	}

}
