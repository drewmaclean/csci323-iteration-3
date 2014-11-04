
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.XChartPanel;



public class RTChart  {

  private Chart chart;
  public static final String SERIES_NAME = "APPL";
  private static List<Integer> xData;
  private static List<Double> yData;
  static DatabaseAccess db;
  static Stock APPL;
  
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
	 //Calls db access and Stock classes 
	 db = new DatabaseAccess();
     APPL = db.getAPPL();
    
     // Sets up panel
     final RTChart myRTChart = new RTChart();
     final XChartPanel chartPanel = myRTChart.buildPanel();

     //Dont really know what this does.
     javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    chart.setChartTitle("Apple Inc. ");
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
    int i =3519;

    //Adds data from APPL.data to yData until end of list=3520
    while (yData.size() <APPL.data.size()) {
      yData.add(APPL.data.get(i));
      i--;
    }

    //Adds 1,2,3,4...etc to xdata until end of Lists
    //Dont know why the xData.add has all that within the parameters can be fixed eventually
    xData.add(xData.get(xData.size() - 1) + 1);
    while (xData.size() > APPL.dates.size()) {
    	
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

