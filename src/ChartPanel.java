import com.xeiam.xchart.*;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class ChartPanel extends JPanel{

    Stock APPL;
    XChartPanel stockPanel;
    Timer timer;

    public ChartPanel() throws SQLException, ClassNotFoundException {

        // Create Chart
        Chart chart = new ChartBuilder().width(800).height(600).title("Stock Game").build();
        chart.getStyleManager().setLegendVisible(false);

        DatabaseAccess db;
        db = new DatabaseAccess();
        APPL = db.readFromDb("aapl");

        Series seris = chart.addSeries("APPL", APPL.transientDates, APPL.transientData);
        seris.setMarker(SeriesMarker.NONE);

        stockPanel = new XChartPanel(chart);

        add(stockPanel);

        timer = new Timer();
        timer.scheduleAtFixedRate(new EventLoop(), 0, 50);
    }

    class EventLoop extends TimerTask {
        public void run() {
            if(APPL.hasNext()) {
                APPL.update();
                stockPanel.updateSeries("APPL", APPL.transientDates, APPL.transientData);
            }
            else{
                timer.cancel();
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new ChartPanel());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
