import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PopulateList {
	DatabaseAccess db;
	static ArrayList<Date> AAPLdates = new ArrayList<Date>();
    static ArrayList<Double> AAPLdata = new ArrayList<Double>();
    static ArrayList<Date> NKEdates,BACdates,COKEdates,COPdates,
    COSTdates,DISdates,Fdates,MSFTdates,YHOOdates;
    static ArrayList<Double> NKEdata,BACdata,COKEdata,COPdata,
    COSTdata,DISdata,Fdata,MSFTdata,YHOOdata;
	public PopulateList() throws ClassNotFoundException, SQLException{
        db = new DatabaseAccess();
        COKEdates = new ArrayList<Date>();
        COKEdata = new ArrayList<Double>();
        NKEdates = new ArrayList<Date>();
        NKEdata = new ArrayList<Double>();
        BACdates = new ArrayList<Date>();
        BACdata = new ArrayList<Double>();
        COPdates = new ArrayList<Date>();
        COPdata = new ArrayList<Double>();
        COSTdates = new ArrayList<Date>();
        COSTdata = new ArrayList<Double>();
        DISdates = new ArrayList<Date>();
        DISdata = new ArrayList<Double>();
        Fdates = new ArrayList<Date>();
        Fdata = new ArrayList<Double>();
        MSFTdates = new ArrayList<Date>();
        MSFTdata = new ArrayList<Double>();
        YHOOdates = new ArrayList<Date>();
        YHOOdata = new ArrayList<Double>();
        
        //APPle Stock
        Stock AAPLstock = db.readFromDb("aapl");
        AAPLdates.addAll(AAPLstock.dates);
        AAPLdata.addAll(AAPLstock.data);
        
        //Coke stock
        Stock Cokestock = db.readFromDb("coke");
        COKEdates.addAll(Cokestock.dates);
        COKEdata.addAll(Cokestock.data);
        
        //Nike Stock
        Stock NKEstock = db.readFromDb("nke");
        NKEdates.addAll(NKEstock.dates);
        NKEdata.addAll(NKEstock.data);
        
        //BAC Stock
        Stock BACstock = db.readFromDb("bac");
        BACdates.addAll(BACstock.dates);
        BACdata.addAll(BACstock.data);
        
        //COP Stock
        Stock COPstock = db.readFromDb("cop");
        COPdates.addAll(COPstock.dates);
        COPdata.addAll(COPstock.data);
        
        //COST Stock
        Stock COSTstock = db.readFromDb("cost");
        COSTdates.addAll(COSTstock.dates);
        COSTdata.addAll(COSTstock.data);
        
        //DIS Stock
        Stock DISstock = db.readFromDb("dis");
        DISdates.addAll(DISstock.dates);
        DISdata.addAll(DISstock.data);
        
        //F Stock
        Stock Fstock = db.readFromDb("f");
        Fdates.addAll(Fstock.dates);
        Fdata.addAll(Fstock.data);
        
        //MSFT Stock
        Stock MSFTstock = db.readFromDb("msft");
        MSFTdates.addAll(MSFTstock.dates);
        MSFTdata.addAll(MSFTstock.data);
        
        //YHOO Stock
        Stock YHOOstock = db.readFromDb("yhoo");
        YHOOdates.addAll(YHOOstock.dates);
        YHOOdata.addAll(YHOOstock.data);
        
        
	}
	  public Stock readFromList(String tickerSymbol){
		  if(tickerSymbol.equals("aapl"))
		  {
			  return new Stock(tickerSymbol, AAPLdates, AAPLdata);
		  }
		  if(tickerSymbol.equals("coke"))
		  {
			  return new Stock(tickerSymbol, COKEdates, COKEdata);
		  }
		  if(tickerSymbol.equals("nke"))
		  {
			  return new Stock(tickerSymbol, NKEdates, NKEdata);
		  }
		  if(tickerSymbol.equals("bac"))
		  {
			  return new Stock(tickerSymbol, BACdates, BACdata);
		  }
		  if(tickerSymbol.equals("cop"))
		  {
			  return new Stock(tickerSymbol, COPdates, COPdata);
		  }
		  if(tickerSymbol.equals("cost"))
		  {
			  return new Stock(tickerSymbol, COSTdates, COSTdata);
		  }
		  if(tickerSymbol.equals("dis"))
		  {
			  return new Stock(tickerSymbol, DISdates, DISdata);
		  }
		  if(tickerSymbol.equals("f"))
		  {
			  return new Stock(tickerSymbol, Fdates, Fdata);
		  }
		  if(tickerSymbol.equals("msft"))
		  {
			  return new Stock(tickerSymbol, MSFTdates, MSFTdata);
		  }
		  else{
			  return new Stock(tickerSymbol, YHOOdates, YHOOdata);
		  }
		 

	  }
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		PopulateList Li= new PopulateList();
		//Verify that the data are all different
		System.out.println(Li.YHOOdata.get(0));
		System.out.println(Li.NKEdata.get(0));
		System.out.println(Li.AAPLdata.get(0));
		System.out.println(Li.Fdata.get(0));
		System.out.println(Li.MSFTdata.get(0));
		System.out.println(Li.COPdata.get(0));
		System.out.println(Li.COSTdata.get(0));
		System.out.println(Li.DISdata.get(0));
		System.out.println(Li.BACdata.get(0));
		System.out.println(Li.COKEdata.get(0));
		
	}

}
