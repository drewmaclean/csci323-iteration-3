package stuff;

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
    //update will now give a non compressed graph
    public void update() {
    	
        transientDates.add(dates.get(i));
        transientData.add(data.get(i));
        i--;
       
        //can remove if user wants a compressed graph just remove the following bit of code
        while(transientDates.size()>10){
        transientDates.remove(0);
        transientData.remove(0);
        }
    }
    //CAN have two functions for user to choose either compressed or non compressed chart
    //If it looks better with just noncompressed then we can remove the following function
    ////////////////////////////////////////////////////////////////////////////////////////
  /*public void updateNonCompressedChart() {
    	
        transientDates.add(dates.get(i));
        transientData.add(data.get(i));
        i--;
       
        while(transientDates.size()>10){
        transientDates.remove(0);
        transientData.remove(0);
        }
    }*/

    public boolean hasNext() {
        return i > 0;
    }
}
