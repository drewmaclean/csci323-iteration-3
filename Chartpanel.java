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
    ArrayList<Stock> stocks1 = new ArrayList<Stock>();
    XChartPanel stockPanel,stockPanel1;
    Chart chart,chart2;
    Timer timer;
    DatabaseAccess db;
    JLabel placeHolder = new JLabel("Please Select a stock");
    boolean zeroStocks = true;
    double currentPrice = 0;
    JLabel stockPrice;

    public ChartPanel() throws SQLException, ClassNotFoundException {
        db = new DatabaseAccess();

        // Create Chart
        chart = new ChartBuilder().width(800).height(300).title("Stock Game").build();
        chart.getStyleManager().setLegendVisible(false);
        
        //Updated from previous version
        //Create Noncompress Chart
        chart2 = new ChartBuilder().width(800).height(300).title("Stock Game").build();
        chart2.getStyleManager().setLegendVisible(false);

        stockPanel = new XChartPanel(chart);
        stockPanel1=new XChartPanel(chart2);
        // Placeholder label
        setMinimumSize(new Dimension(800,600));
        placeHolder.setMinimumSize(new Dimension(800,600));
        add(placeHolder);
    }

    public void addStock (String tickerSymbol) {
        if(zeroStocks){
            remove(placeHolder);
            add(stockPanel);
            add(stockPanel1);
        }
        //For compressed chart
        Stock stock = db.readFromDb(tickerSymbol);
        Series series = chart.addSeries(stock.tickerSymbol, stock.transientDates, stock.transientData);
        series.setMarker(SeriesMarker.NONE);
        stocks.add(stock);
        
        ////Updated from previous version
        //For non compressed chart
        Stock stock1 = db.readFromDb(tickerSymbol);
        Series series1 = chart2.addSeries(stock1.tickerSymbol, stock1.transientDates, stock1.transientData);
        series1.setMarker(SeriesMarker.NONE);
        stocks1.add(stock1);
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
        	//For compressed graph
            for(Stock s: stocks ){
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
            
            ////Updated from previous version
            //For non compressed graph
            for(Stock s1: stocks1 ){
                if(!s1.hasNext()){
                    timer.cancel();
                }
                
                s1.updateNonCompressedChart();
                stockPanel1.updateSeries(s1.tickerSymbol, s1.transientDates, s1.transientData);
              
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
