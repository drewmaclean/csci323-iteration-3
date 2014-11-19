import junit.framework.TestCase;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class DatabaseTest extends TestCase {
	private static String driverName= "org.gjt.mm.mysql.Driver";
	private static String serverName= "sql5.freemysqlhosting.net";
	private static String schema= "sql555866";
	private static String userName="sql555866";
	private static String url= "jdbc:mysql://" + serverName +  "/" + schema;
	private static String password= "vL3*mR8%";


	public void testConnection() throws Exception {
		Connection conn = null; 

		try {    
			Class.forName(driverName);    
			System.out.println("Connected to general database.");    
			conn = DriverManager.getConnection(url, userName, password); 
		} catch (Exception e) {    
			e.printStackTrace();    
		} finally {    
			if (conn != null) {    
				try {    
					conn.close();    
				} catch (SQLException e) {    

				}    
			}    
		}            
	}
	
	public void testAAPL() throws Exception {
		String table = "aapl";
		testStocks(table);
	}
	
	public void testBAC() throws Exception {
		String table = "bac";
		testStocks(table);
	}
	
	public void testCOKE() throws Exception {
		String table = "coke";
		testStocks(table);
	}
	
	public void testCOP() throws Exception {
		String table = "cop";
		testStocks(table);
	}
	
	public void testCOST() throws Exception {
		String table = "cost";
		testStocks(table);
	}
	
	public void testDIS() throws Exception {
		String table = "dis";
		testStocks(table);
	}
	
	public void testF() throws Exception {
		String table = "f";
		testStocks(table);
	}
	
	public void testMSFT() throws Exception {
		String table = "msft";
		testStocks(table);
	}
	
	public void testNKE() throws Exception {
		String table = "nke";
		testStocks(table);
	}
	
	public void testYHOO() throws Exception {
		String table = "yhoo";
		testStocks(table);
	}
	
	public void testStocks(String table) throws Exception {
		Connection conn = null; 

		try {
			
			Class.forName(driverName);    
			  
			conn = DriverManager.getConnection(url, userName, password);
			Statement State = conn.createStatement();
			
			String str = String.format("SELECT * FROM " + table);
            State.executeQuery(str);
            ResultSet RS = State.getResultSet(); // database scanner
            ArrayList<Date> dates = new ArrayList<Date>();
            ArrayList<Double> data = new ArrayList<Double>();
            DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

            while (RS.next()) {
                dates.add(sdf.parse(RS.getString(1)));
                data.add(RS.getDouble(2));
            }
            System.out.println("Connected to database: " + table);  
		} catch (Exception e) {    
			e.printStackTrace();    
		} finally {    
			if (conn != null) {    
				try {    
					conn.close();    
				} catch (SQLException e) {    

				}    
			}    
		}            
	}
}