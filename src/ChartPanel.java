import com.xeiam.xchart.*;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class ChartPanel extends JPanel{

    ArrayList<Stock> stocks = new ArrayList<Stock>();
    XChartPanel stockPanel;
    Chart chart;
    Timer timer;
    DatabaseAccess db;
    JLabel placeHolder = new JLabel("Please Select a stock");
    boolean zeroStocks = true;

    public ChartPanel() throws SQLException, ClassNotFoundException {
        db = new DatabaseAccess();

        // Create Chart
        chart = new ChartBuilder().width(800).height(600).title("Stock Game").build();
        chart.getStyleManager().setLegendVisible(false);

        stockPanel = new XChartPanel(chart);

        // Placeholder label
        setMinimumSize(new Dimension(800,600));
        placeHolder.setMinimumSize(new Dimension(800,600));
        add(placeHolder);
    }

    public void addStock (String tickerSymbol) {
        if(zeroStocks){
            remove(placeHolder);
            add(stockPanel);
        }
        Stock stock = db.readFromDb(tickerSymbol);
        Series series = chart.addSeries(stock.tickerSymbol, stock.transientDates, stock.transientData);
        series.setMarker(SeriesMarker.NONE);
        stocks.add(stock);
    }

    public void play() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new EventLoop(), 0, 50);
    }

    class EventLoop extends TimerTask {
        public void run() {
            for(Stock s: stocks){
                if(!s.hasNext()){
                    timer.cancel();
                }
                s.update();
                stockPanel.updateSeries(s.tickerSymbol, s.transientDates, s.transientData);
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ChartPanel p1 = new ChartPanel();
        frame.add(p1);
        p1.addStock("aapl");
        //p1.play();


        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
