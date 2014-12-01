import com.xeiam.xchart.*;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.StyleManager.LegendPosition;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChartPanel extends JPanel{

    ArrayList<Stock> stocks = new ArrayList<Stock>();
    ArrayList<Stock> stocks1 = new ArrayList<Stock>();
    ArrayList<SeriesColor> seriesColorAL = new ArrayList<SeriesColor>();
    XChartPanel stockPanel,stockPanel1;
    Chart chart,chart2;
    Timer timer;
    DatabaseAccess db;
    JLabel placeHolder = new JLabel("Please Select a stock");
    boolean zeroStocks = true;
    double currentPrice = 0;
    JLabel stockPrice;
    boolean running = false;
    MainGame mg;

    public ChartPanel(MainGame mg) throws SQLException, ClassNotFoundException, ParseException {

        this.mg = mg;

        seriesColorAL.add(SeriesColor.BLUE);
    	seriesColorAL.add(SeriesColor.GREEN);
    	seriesColorAL.add(SeriesColor.PURPLE);
    	seriesColorAL.add(SeriesColor.ORANGE);
        
    	setBackground(Color.WHITE);

        db = new DatabaseAccess();

        // Create Chart
        chart = new ChartBuilder().theme(ChartTheme.GGPlot2).width(800).height(300).title("Stock price USD").build();
        chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyleManager().setLegendVisible(true);
        chart.getStyleManager().setDecimalPattern("#0.00");
        
        //Updated from previous version
        //Create non-compressed Chart
        chart2 = new ChartBuilder().theme(ChartTheme.GGPlot2).width(800).height(300).title("30 day moving window").build();
        chart2.getStyleManager().setLegendVisible(false);
        chart2.getStyleManager().setDecimalPattern("#0.00");

        stockPanel  = new XChartPanel(chart);
        stockPanel1 = new XChartPanel(chart2);

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
        // Add to nonCompressed chart
        Stock stock = db.readFromDb(tickerSymbol);
        Series series = chart.addSeries(stock.tickerSymbol,
                                        stock.transientNonCompressedDates,
                                        stock.transientNonCompressedData);
        series.setMarker(SeriesMarker.NONE);
        series.setLineColor(seriesColorAL.get(stocks.size())); // Check how many MainGamestocks have been added and get a color


        // add indicator
        Series indicatorSeries = chart.addSeries(stock.tickerSymbol+" ind", stock.indicatorDates, stock.indicatorData);
        indicatorSeries.setMarker(SeriesMarker.NONE);
        indicatorSeries.setLineColor(SeriesColor.RED); // all indicator lines will be red


        // Add to compressed chart
        Series series1 = chart2.addSeries(stock.tickerSymbol,
                stock.transientCompressedDates,
                stock.transientCompressedData);
        series1.setMarker(SeriesMarker.NONE);
        series1.setLineColor(seriesColorAL.get(stocks.size())); // Check how many MainGamestocks have been added and get a color


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
            if(s.tickerSymbol.equals("aapl"))
            {
            	if(MainGame.AAPLCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("coke"))
            {
            	if(MainGame.COKECheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("nke"))
            {
            	if(MainGame.NKECheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("bac"))
            {
            	if(MainGame.BACCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("cop"))
            {
            	if(MainGame.COPCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("cost"))
            {
            	if(MainGame.COSTCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("dis"))
            {
            	if(MainGame.DISCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("f"))
            {
            	if(MainGame.FCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("msft"))
            {
            	if(MainGame.MSFTCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            else if(s.tickerSymbol.equals("yhoo"))
            {
            	if(MainGame.YHOOCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
            	}
            }
            i++;
        }
    	return currentPrice;
    }
    
    public double sellStock() {
    	int i = 0;
    	for(Stock s: stocks){
            s.sell();
            if(s.tickerSymbol.equals("aapl"))
            {
            	if(MainGame.AAPLCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("coke"))
            {
            	if(MainGame.COKECheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("nke"))
            {
            	if(MainGame.NKECheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("bac"))
            {
            	if(MainGame.BACCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("cop"))
            {
            	if(MainGame.COPCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("cost"))
            {
            	if(MainGame.COSTCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("dis"))
            {
            	if(MainGame.DISCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("f"))
            {
            	if(MainGame.FCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("msft"))
            {
            	if(MainGame.MSFTCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            else if(s.tickerSymbol.equals("yhoo"))
            {
            	if(MainGame.YHOOCheck.isSelected())
            	{
            		DecimalFormat df = new DecimalFormat("#.##");
                    MainGame.stockAL.get(i).setText((s.tickerSymbol + " : " + s.buyPrice + " : " + s.sellPrice + " : " + df.format(s.valueChange)));
                    if(s.valueChange > 0){
                    	MainGame.stockAL.get(i).setForeground(Color.GREEN);
                    } else {
                    	MainGame.stockAL.get(i).setForeground(Color.RED);
                    }
            	}
            }
            i++;
        }
    	return currentPrice;
    }
    
    public void play(int Speed) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new EventLoop(), 0, Math.abs(Speed));
        running = true;
    }
    
    public void pause(){
    	timer.cancel();
    	running = false;
    }
    
    public boolean isRunning() {
    	return running;
    }

    class EventLoop extends TimerTask {
        public void run() {
        	//For compressed graph
            for(Stock s: stocks){
                if(!s.hasNext()){
                    timer.cancel();
                }
                s.update();
                s.updateIndicator(MainGame.movingAverageSelected);

                stockPanel.updateSeries(s.tickerSymbol, s.transientNonCompressedDates, s.transientNonCompressedData);
                stockPanel.updateSeries(s.tickerSymbol+" ind", s.indicatorDates, s.indicatorData);
                stockPanel1.updateSeries(s.tickerSymbol, s.transientCompressedDates, s.transientCompressedData);

                //update stock
                mg.update();

            }
        }
    }
}
