import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stock {
    public List<Date> dates;
    public List<Double> data;

    public List<Date> transientDates;
    public List<Double> transientData;

    ArrayList<Double> buyData = new ArrayList<Double>();; //?
    public String tickerSymbol;

    private int i;
    
    double currentPrice = 0;
    double buyPrice = 0;
    double sellPrice = 0;
    double valueChange = 0;

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
    	currentPrice = data.get(i);
        transientDates.add(dates.get(i));
        transientData.add(data.get(i));
        i--;
    }
    
    public void buy() {
    	buyPrice = currentPrice;
        for(int j=0; j < transientDates.size(); j++){
        	buyData.add(buyPrice);
        }
    }

    public double sell() {
    	sellPrice = currentPrice;
    	valueChange += sellPrice - buyPrice;
    	return valueChange;
    }
    
    public boolean hasNext() {
        return i > 0;
    }
}
