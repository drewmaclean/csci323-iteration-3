import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stock {
    public List<Date> dates,
                      indicatorDates,
                      indicatorCompressedDates,
                      transientNonCompressedDates,
                      transientCompressedDates,
    				  buyIndicatorDates;
    public List<Double> data,
                        indicatorData,
                        indicatorCompressedData,
                        transientNonCompressedData,
                        transientCompressedData,
                        buyIndicatorData;

    ArrayList<Double> buyData = new ArrayList<Double>();
    ArrayList<Purchase> buys = new ArrayList<Purchase>();
    public String tickerSymbol;

    private int i;
    
    double currentPrice = 0;
    double buyPrice     = 0;
    double sellPrice    = 0;
    double valueChange  = 0;
    boolean isOwned = false;

    public Stock(String tickerSymbol, ArrayList<Date> dates, ArrayList<Double> data){
        if(dates.size() != data.size())
            throw new IllegalArgumentException("Dates and data are not the same size ");

        i = dates.size()-1;

        this.tickerSymbol = tickerSymbol;
        this.dates        = dates;
        this.data         = data;

        transientNonCompressedDates = new ArrayList<Date>();
        transientCompressedDates    = new ArrayList<Date>();
        indicatorDates              = new ArrayList<Date>();
        indicatorCompressedDates	= new ArrayList<Date>();
        buyIndicatorDates			= new ArrayList<Date>();
        transientCompressedData     = new ArrayList<Double>();
        transientNonCompressedData  = new ArrayList<Double>();
        indicatorData               = new ArrayList<Double>();
        indicatorCompressedData		= new ArrayList<Double>();
        buyIndicatorData			= new ArrayList<Double>();

        // add two elements to each chart
        update();
        update();

        updateIndicator(MainGame.movingAverageSelected);
        updateIndicator(MainGame.movingAverageSelected);
        
        updateBuyIndicator();
        updateBuyIndicator();
    }

    public void updateIndicator(int movingAveragePeriod) {
        double average = 0;
        ArrayList<Double> tempAverage = new ArrayList<Double>();
        if (i < (dates.size()-movingAveragePeriod-1)) {
            for(int j=0; j < movingAveragePeriod; j++) {
                tempAverage.add(data.get(i+j));
            }
            /// find moving average
            double sum = 0;
            for (Double indData : tempAverage) {
                sum += indData;
            }
            average = sum / tempAverage.size();
            ///
            indicatorDates.add(dates.get(i));
            indicatorData.add(average);
            indicatorCompressedDates.add(dates.get(i));
            indicatorCompressedData.add(average);
        } else {
            indicatorData.add(data.get(i)-.5);
            indicatorDates.add(dates.get(i));
            indicatorCompressedData.add(data.get(i)-.5);
            indicatorCompressedDates.add(dates.get(i));
        }
        while(indicatorCompressedData.size()>30){
        	indicatorCompressedData.remove(0);
        	indicatorCompressedDates.remove(0);
        }
    }

    public void update() {
    	currentPrice = data.get(i);
        transientNonCompressedDates.add(dates.get(i));
        transientNonCompressedData.add(data.get(i));

        transientCompressedDates.add(dates.get(i));
        transientCompressedData.add(data.get(i));

        while(transientCompressedData.size()>30){
            transientCompressedData.remove(0);
            transientCompressedDates.remove(0);
        }
        

        i--;
    }
    
    public void updateBuyIndicator() {
    	if(isOwned){
    		buyIndicatorData.add(buyPrice);
    	} else {
    		buyIndicatorData.add(data.get(i));
    	}
    		buyIndicatorDates.add(dates.get(i));
    }

    public void registerPurchase(Purchase p) {

    }

    public void buy() {
    	buyPrice = currentPrice;
        for(int j=0; j < transientNonCompressedDates.size(); j++){
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
    
