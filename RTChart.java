import java.sql.SQLException;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import com.xeiam.xchart.*;


//Here I changed a bit of jesse's code in order
//For it to work inside of multiple panels simultaneously. 
public class RTChart extends JPanel  {

  private Chart chart;
  public static String SERIES_NAME = "";
  private static List<Integer> xData;
  private static List<Double> yData;
  static DatabaseAccess db;

  	public static Stock MainStock;
  	public static String ChartTitle = "";
  	
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
	  //If statements that get the information from the GUI class
	  //this will determine which graph to display and will display
	  //it seperately.
	  String SelectedGraph = GUI.CurrentGraph;
	  
	  if(SelectedGraph.equals("AAPL"))
	  {
		  ChartTitle = "Apple Inc.";
		  SERIES_NAME = "AAPL";
	  }
	  else if(SelectedGraph.equals("BAC"))
	  {
		  ChartTitle = "Bank of America";
		  SERIES_NAME = "bac";
	  }
	  else if(SelectedGraph.equals("COKE"))
	  {
		  ChartTitle = "Coca-Cola";
		  SERIES_NAME = "coke";
	  }
	  else if(SelectedGraph.equals("COP"))
	  {
		  ChartTitle = "ConocoPhillips";
		  SERIES_NAME = "cop";
	  }
	  else if(SelectedGraph.equals("COST"))
	  {
		  ChartTitle = "Costco";
		  SERIES_NAME = "cost";
	  }
	  else if(SelectedGraph.equals("DIS"))
	  {
		  ChartTitle = "Walt Disney";
		  SERIES_NAME = "dis";
	  }
	  else if(SelectedGraph.equals("F"))
	  {
		  ChartTitle = "Facebook";
		  SERIES_NAME = "f";
	  }
	  else if(SelectedGraph.equals("MSFT"))
	  {
		  ChartTitle = "Microsoft";
		  SERIES_NAME = "msft";
	  }
	  else if(SelectedGraph.equals("NKE"))
	  {
		  ChartTitle = "Nike";
		  SERIES_NAME = "nke";
	  }
	  else if(SelectedGraph.equals("YHOO"))
	  {
		  ChartTitle = "Yahoo";
		  SERIES_NAME = "yhoo";
	  }
	  
	
	 //Calls db access and Stock classes 
	 db = new DatabaseAccess();
     MainStock = db.getAPPL(SERIES_NAME);
    
     // Sets up panel
     final RTChart myRTChart = new RTChart();
     final XChartPanel chartPanel = myRTChart.buildPanel();

     //Dont really know what this does.
     javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(chartPanel);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
      }
    });

    // Timertask for adding of nxt data points
    TimerTask chartUpdaterTask = new TimerTask() {

      @Override
      public void run() {

    	//IMPORTANT this is where that data will update using timer.
    	myRTChart.updateData();
        chartPanel.updateSeries(SERIES_NAME, myRTChart.getxData(), myRTChart.getyData());

      }
    };
    //Timer that will decide when to add next data points
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);

  }
 //Builds chart
  public XChartPanel buildPanel() {

    return new XChartPanel(getChart());
  }

  public Chart getChart() {
	 
	//Creates the first two data pts which are random(y is random
	//Could be a bug and eventually will not be needed(the functions called)
	//Replace w/ actually data from List eventually
    xData = getMonotonicallyIncreasingData(1);
    yData = getRandomData(1);
    
    // Create Chart with two data points random data points need to find away to 
    //remove the first two data pts(Maybe)..
    Chart chart = new Chart(800, 800);
    chart.setChartTitle(ChartTitle);
    chart.setXAxisTitle("Day");
    chart.setYAxisTitle("Price($)");
    chart.addSeries(SERIES_NAME, xData, yData);

    return chart;
  }
  
//Not important it is used for the first two data points
//but we can find a way around not using this eventually.  
  private List<Double> getRandomData(int numPoints) {

    List<Double> data = new ArrayList<Double>();
    //data.addAll(data);
    for (int i = 0; i < numPoints; i++) {
      data.add(numPoints*100.00);
    }
    return data;
  }
  
//Not important it is used for the first two data points 
//but we can find a way around not using this eventually.
  private List<Integer> getMonotonicallyIncreasingData(int numPoints) {
	  
    List<Integer> data = new ArrayList<Integer>();
    for (int i = 0; i < numPoints; i++) {
      data.add(i);
    }
    return data;
  }

  //IMPORTANT this is where the timer task will update the rest of the graph with 
  //The data from the apple stocks data
  public void updateData() {

    
    //Instantiate i with size of List
    int i =2514;//3519;

    //Adds data from APPL.data to yData until end of list=3520
    while (yData.size() <MainStock.data.size()) {
      yData.add(MainStock.data.get(i));
      i--;
    }

    //Adds 1,2,3,4...etc to xdata until end of Lists
    //Dont know why the xData.add has all that within the parameters can be fixed eventually
    xData.add(xData.get(xData.size() - 1) + 1);
    while (xData.size() > MainStock.dates.size()) {
    	
      //Dont know why this is here but when removed graph dont function properly
      xData.remove(0);
    }
  }
  
  //These are needed and are generated from the updateData() function
  public List<Double> getyData() {
	 
    return yData;
  }
 //These are needed and are generated from the updateData() function
  public List<Integer> getxData() {

    return xData;
  }
  
  
}