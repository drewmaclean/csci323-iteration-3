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
    
    ArrayList<Date> indicatorDates;
	ArrayList<Double> indicatorData;

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
        
        indicatorDates = new ArrayList<Date>();
        indicatorData = new ArrayList<Double>();

        // add two elements to each chart
        update();
        update();
        
        
        ////updated from previous versions
        updateNonCompressedChart(); 
        updateNonCompressedChart();
        
        updateIndicator(MainGame.movingAverageSelected);
        updateIndicator(MainGame.movingAverageSelected);
        
        

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
    	} else {
    		indicatorData.add(data.get(i)-.5);
    		indicatorDates.add(dates.get(i));
    	}
    }

    public void update() {
    	currentPrice = data.get(i);
        transientDates.add(dates.get(i));
        transientData.add(data.get(i));
        i--;
       
    }
    ////Updated from previous versions
    public void updateNonCompressedChart() {
    	
        transientDates.add(dates.get(i));
        transientData.add(data.get(i));
        i--;
       
        while(transientDates.size()>10){
        transientDates.remove(0);
        transientData.remove(0);
        }
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
    
