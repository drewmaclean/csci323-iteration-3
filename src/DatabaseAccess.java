import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class DatabaseAccess {
    private static Connection conn;

    // Load the MySQL JDBC driver
    private static String driverName,
                          serverName,
                          schema,
                          url,
                          userName,
                          password;
    private HashMap<String,Stock> stocks;

    public DatabaseAccess() throws SQLException, ClassNotFoundException, ParseException {
        driverName = "org.gjt.mm.mysql.Driver";
        serverName = "sql5.freemysqlhosting.net";
        schema = "sql555866";
        url = "jdbc:mysql://" + serverName +  "/" + schema;
        userName = "sql555866";
        password = "vL3*mR8%";
        Class.forName(driverName);

        stocks = new HashMap<String, Stock>();

        openConnection();

        // read all stocks into a hashMap for quick access

        String[] tickerSymbols = new String[] {"AAPL","COKE","NKE", "BAC","COP","COST","DIS","F","MSFT","YHOO"};
        DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

        for(String tickerSymbol : tickerSymbols){

            tickerSymbol = tickerSymbol.toLowerCase();

            Statement State = conn.createStatement();

            String str = String.format("SELECT * FROM " + tickerSymbol);
            State.executeQuery(str);
            ResultSet RS = State.getResultSet(); // database scanner
            ArrayList<Date> dates = new ArrayList<Date>();
            ArrayList<Double> data = new ArrayList<Double>();

            while (RS.next()) {
                dates.add(sdf.parse(RS.getString(1)));
                data.add(RS.getDouble(5)); // get the close price
            }

            stocks.put(tickerSymbol, new Stock(tickerSymbol, dates, data));
        }

        closeConnection();
    }

    private void openConnection() throws SQLException {
        conn = DriverManager.getConnection(url, userName, password);
    }

    private void closeConnection() throws SQLException {
        conn.close();
    }

    public Stock readFromDb(String tickerSymbol){
        return stocks.get(tickerSymbol);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        DatabaseAccess db = new DatabaseAccess();
        db.readFromDb("appl");
    }
}
