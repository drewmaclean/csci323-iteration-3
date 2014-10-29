import java.sql.SQLException;

import com.xeiam.xchart.*;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Create Chart
        Chart chart = new ChartBuilder().width(800).height(600).title("Year Scale").build();
        chart.getStyleManager().setLegendVisible(false);
        chart.getStyleManager().isPlotTicksMarksVisible();

        DatabaseAccess db = new DatabaseAccess();
        Stock APPL = db.getAPPL();

        //System.out.println(APPL.data.toString());

        Series seris = chart.addSeries("APPL", APPL.dates, APPL.data);
        seris.setMarker(SeriesMarker.NONE);

        // Show chart
        new SwingWrapper(chart).displayChart();
    }

}
