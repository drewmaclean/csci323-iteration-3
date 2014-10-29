import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stock {
    public List<Date> dates;
    public List<Double> data;

    public Stock(ArrayList<Date> dates, ArrayList<Double> data){
        this.dates = dates;
        this.data = data;
    }
}
