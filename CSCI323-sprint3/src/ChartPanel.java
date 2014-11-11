import com.xeiam.xchart.*;

import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
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
    double currentPrice = 0;
    JLabel stockPrice;

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
    
    public double buyStock() {
    	int i = 0;
    	for(Stock s: stocks){
            s.buy();
            //ArrayList<Double> buyData = new ArrayList<Double>();
            //for(int j=0; j < s.transientDates.size(); j++){
            //	buyData.add(s.buyPrice);
            //}
            //Series series = chart.addSeries("app", s.transientDates, buyData);
            DecimalFormat df = new DecimalFormat("#.##");
            MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            i++;
        }
    	return currentPrice;
    }
    
    public double sellStock() {
    	int i = 0;
    	for(Stock s: stocks){
            s.sell();
            
            DecimalFormat df = new DecimalFormat("#.##");
            MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            if(s.valueChange > 0){
            	MainGame.stockAL.get(i).setForeground(Color.GREEN);
            } else {
            	MainGame.stockAL.get(i).setForeground(Color.RED);
            }
            i++;
        }
    	return currentPrice;
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
                //for(int j=0; j < s.transientDates.size(); j++){
                //	s.buyData.add(10.0);
                //}
                //stockPanel.updateSeries(s.tickerSymbol, s.transientDates, s.buyData);
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
