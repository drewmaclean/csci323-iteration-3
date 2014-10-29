import junit.framework.TestCase;
import java.sql.*; 

public class DatabaseTest extends TestCase {
	private static String driverName= "org.gjt.mm.mysql.Driver";
	private static String serverName= "sql5.freemysqlhosting.net";
	private static String schema= "sql555866";
	private static String userName="sql555866";
	private static String url= "jdbc:mysql://" + serverName +  "/" + schema;
	private static String password= "vL3*mR8%";


	public void testname() throws Exception {


		Connection conn = null; 

		try {    
			Class.forName(driverName);    
			System.out.println("Connected to database.");    
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
}


