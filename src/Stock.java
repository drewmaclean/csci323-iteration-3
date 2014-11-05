import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stock {
    public List<Date> dates;
    public List<Double> data;

    public List<Date> transientDates;
    public List<Double> transientData;

    public String tickerSymbol;

    private int i;

    public Stock(String tickerSymbol, ArrayList<Date> dates, ArrayList<Double> data){
        if(dates.size() != data.size())
            throw new IllegalArgumentException("Dates and data are not the same size ");

        i = dates.size()-1;

        this.tickerSymbol = tickerSymbol;
        this.dates = dates;
        this.data  = data;

        transientDates = new ArrayList<Date>();
        transientData  = new ArrayList<Double>();

        // add two elements to each chart
        update();
        update();
    }

    public void update() {
        transientDates.add(dates.get(i));
        transientData.add(data.get(i));
        i--;
    }

    public boolean hasNext() {
        return i > 0;
    }
}
