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
    private static String table,
                          keyField,
                          driverName,
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

    // This method returns some data very possibly connected with Apple somehow I have no idea what I have no idea
    // what it actually is
    public Stock getAPPL(){
        try{
            openConnection();

            // this should probably be made a class level variable at some point
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
            for (String[] aRowAL : rowAL) {

                System.out.println(aRowAL[0] + "\t" + aRowAL[1] + "\t" + aRowAL[2] + "\t" + aRowAL[3] + "\t" + aRowAL[4] + "\t" + aRowAL[5] + "\n");
            }
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        } finally {
            db.closeConnection();
        }

    }

}
