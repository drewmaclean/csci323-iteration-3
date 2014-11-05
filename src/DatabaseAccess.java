import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DatabaseAccess {
    private static Connection conn;

    // Load the MySQL JDBC driver
    private static String driverName,
                          serverName,
                          schema,
                          url,
                          userName,
                          password;

    public DatabaseAccess() throws SQLException, ClassNotFoundException {
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

    public Stock readFromDb(String table){
        try{
            openConnection();

            // this should probably be made a class level variable at some point
            DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

            Statement State = conn.createStatement();

            String str = String.format("SELECT * FROM " + table);
            State.executeQuery(str);
            ResultSet RS = State.getResultSet(); // database scanner
            ArrayList<Date> dates = new ArrayList<Date>();
            ArrayList<Double> data = new ArrayList<Double>();

            while (RS.next()) {
                dates.add(sdf.parse(RS.getString(1)));
                data.add(RS.getDouble(2));
            }
            closeConnection();

            return new Stock(dates, data);

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return null;
        }
    }
}
