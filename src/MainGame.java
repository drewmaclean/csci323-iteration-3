//import javafx.scene.control.Tooltip;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;


public class MainGame extends JPanel implements ActionListener, ItemListener {

    //Define our global scale variables
	static ArrayList<JLabel> stockAL = new ArrayList<JLabel>();
	static ArrayList<String> AmountList = new ArrayList<String>();
	JButton NewWindowButton,
			BuyButton,
			SellButton;
	public JLabel stockPriceLabel;
    JComboBox ChartTypeCB = new JComboBox();
    public static String CurrentGraph = "";
    ChartPanel cp;
    
    static JCheckBox AAPLCheck = new JCheckBox("AAPL");
    static JCheckBox COKECheck = new JCheckBox("COKE");
    static JCheckBox NKECheck = new JCheckBox("NKE");
    static JCheckBox BACCheck = new JCheckBox("BAC");
    static JCheckBox COPCheck = new JCheckBox("COP");
    static JCheckBox COSTCheck = new JCheckBox("COST");
    static JCheckBox DISCheck = new JCheckBox("DIS");
    static JCheckBox FCheck = new JCheckBox("F");
    static JCheckBox MSFTCheck = new JCheckBox("MSFT");
    static JCheckBox YHOOCheck = new JCheckBox("YHOO");
    
    JButton SlowSpeedButton = new JButton("Slow");
    JButton MediumSpeedButton = new JButton("Medium");
    JButton FastSpeedButton = new JButton("Fast");
    JLabel SpeedLabel = new JLabel("Choose Speed");
    
    int Speed = 0;
    
    JCheckBoxList cbList = new JCheckBoxList();

    public MainGame() throws SQLException, ClassNotFoundException {
        //All this is just layout stuff and declaring the event listener
        setLayout(null);

        setPreferredSize(new Dimension(1100, 630));

        JLabel lblChartTypes = new JLabel("Chart Types:");
        lblChartTypes.setBounds(15, 10, 100, 14);
        add(lblChartTypes);

        ChartTypeCB.setModel(new DefaultComboBoxModel(new String[] {"Line Chart (Default)","Pie Chart", "Block Chart"}));
        ChartTypeCB.setToolTipText("");
        ChartTypeCB.setBounds(10, 35, 120, 20);
        add(ChartTypeCB);

        JLabel lblStockInformation = new JLabel("Stock Information:");
        lblStockInformation.setBounds(15, 70, 120, 14);
        add(lblStockInformation);

        
        JCheckBox[] stocks = {StockCheckBox("AAPL"), StockCheckBox("BAC"), StockCheckBox("COKE"),
                StockCheckBox("COP"), StockCheckBox("COST"), StockCheckBox("DIS"), StockCheckBox("F"),
                StockCheckBox("MSFT"), StockCheckBox("NKE"), StockCheckBox("YHOO")};
        cbList.setListData(stocks);

        JScrollPane jsp = new JScrollPane(cbList);
        jsp.setBounds(10, 90, 120, 150);
        add(jsp);

        NewWindowButton = new JButton("play");
        NewWindowButton.setEnabled(false);
        NewWindowButton.addActionListener(this);
        NewWindowButton.setBounds(10, 270, 100, 30);
        add(NewWindowButton);
        
        BuyButton = new JButton("buy");
        BuyButton.addActionListener(this);
        BuyButton.setBounds(10, 310, 100, 30);
        add(BuyButton);
        BuyButton.setEnabled(false);
        
        SellButton = new JButton("sell");
        SellButton.addActionListener(this);
        SellButton.setBounds(10, 350, 100, 30);
        add(SellButton);
        SellButton.setEnabled(false);
        
        
        
        stockPriceLabel = new JLabel("Name - Buy - Sell - Profit");
        stockPriceLabel.setBounds(10, 404, 200, 30);
        add(stockPriceLabel);
        
        for (int i = 0; i < 10; i++) {
        	stockAL.add(stockPriceLabel = new JLabel());
        	stockAL.get(i).setBounds(10, (420 + (i * 20)), 200, 30);
        	add(stockAL.get(i));
        }


        cp = new ChartPanel();
        cp.setBounds(262,11, 800, 600);
        add(cp);
        
        
        AAPLCheck.setEnabled(false);
        AAPLCheck.setBounds(136, 88, 61, 23);
        add(AAPLCheck);
     
        
        
        COKECheck.setEnabled(false);
        COKECheck.setBounds(136, 114, 61, 23);
        add(COKECheck);
    
        
        
        NKECheck.setEnabled(false);
        NKECheck.setBounds(136, 140, 61, 23);
        add(NKECheck);
      
        
        
        BACCheck.setEnabled(false);
        BACCheck.setBounds(136, 166, 61, 23);
        add(BACCheck);
     
        
        
        COPCheck.setEnabled(false);
        COPCheck.setBounds(136, 192, 61, 23);
        add(COPCheck);
     
        
        
        COSTCheck.setEnabled(false);
        COSTCheck.setBounds(200, 88, 74, 23);
        add(COSTCheck);
  
        
       
        DISCheck.setEnabled(false);
        DISCheck.setBounds(200, 114, 89, 23);
        add(DISCheck);
   
        
        
        FCheck.setEnabled(false);
        FCheck.setBounds(200, 140, 74, 23);
        add(FCheck);
  
        
        
        MSFTCheck.setEnabled(false);
        MSFTCheck.setBounds(200, 166, 89, 23);
        add(MSFTCheck);

        
        
        YHOOCheck.setEnabled(false);
        YHOOCheck.setBounds(200, 192, 74, 23);
        add(YHOOCheck);
        
        JLabel lblNewLabel = new JLabel("Select to Buy/Sell");
        lblNewLabel.setBounds(136, 70, 97, 14);
        add(lblNewLabel);
        
        
        SlowSpeedButton.setBounds(163, 274, 89, 23);
        add(SlowSpeedButton);
        SlowSpeedButton.addActionListener(this);
        
        MediumSpeedButton.setBounds(163, 314, 89, 23);
        add(MediumSpeedButton);
        MediumSpeedButton.addActionListener(this);
        
       
        FastSpeedButton.setBounds(163, 354, 89, 23);
        add(FastSpeedButton);
        FastSpeedButton.addActionListener(this);
        
        
        SpeedLabel.setBounds(163, 249, 89, 14);
        add(SpeedLabel);


    }

    private JCheckBox StockCheckBox(String s){
        JCheckBox check = new JCheckBox(s);
        check.addItemListener(this);
        return check;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource() == NewWindowButton) {
    		cp.play(Speed);
    		NewWindowButton.setEnabled(false);
    		BuyButton.setEnabled(true);
    	}
    	else if(e.getSource() == BuyButton) {
    		cp.buyStock();
    		SellButton.setEnabled(true);
    		BuyButton.setEnabled(false);
    	}
    	else if(e.getSource() == SellButton) {
    		cp.sellStock();
    		SellButton.setEnabled(false);
    		BuyButton.setEnabled(true);
    	}
    	else if(e.getSource() == SlowSpeedButton) {
    		Speed = 200;
    		NewWindowButton.setEnabled(true);
    		SlowSpeedButton.setEnabled(false);
    		MediumSpeedButton.setEnabled(false);
    		FastSpeedButton.setEnabled(false);
    		SpeedLabel.setVisible(false);
    	}
    	else if(e.getSource() == MediumSpeedButton) {
    		Speed = 100;
    		NewWindowButton.setEnabled(true);
    		SlowSpeedButton.setEnabled(false);
    		MediumSpeedButton.setEnabled(false);
    		FastSpeedButton.setEnabled(false);
    		SpeedLabel.setVisible(false);
    	}
    	else if(e.getSource() == FastSpeedButton) {
    		Speed = 50;
    		NewWindowButton.setEnabled(true);
    		SlowSpeedButton.setEnabled(false);
    		MediumSpeedButton.setEnabled(false);
    		FastSpeedButton.setEnabled(false);
    		SpeedLabel.setVisible(false);
    	}
    	
    	
    	
    }
    

    
    

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox c = (JCheckBox) e.getSource();
        
        AmountList = new ArrayList<String>();
        if(c.isSelected()){
        	AmountList = new ArrayList<String>();
        	if(c.getText().equals("AAPL"))
        	{
        		AAPLCheck.setEnabled(true);
        		
        	}
        	else if(c.getText().equals("COKE"))
        	{
        		COKECheck.setEnabled(true);
        	}
        	else if(c.getText().equals("NKE"))
        	{
        		NKECheck.setEnabled(true);
        	}
        	else if(c.getText().equals("BAC"))
        	{
        		BACCheck.setEnabled(true);
        	}
        	else if(c.getText().equals("COP"))
        	{
        		COPCheck.setEnabled(true);
        	}
        	else if(c.getText().equals("COST"))
        	{
        		COSTCheck.setEnabled(true);
        	}
        	else if(c.getText().equals("DIS"))
        	{
        		DISCheck.setEnabled(true);
        	}
        	else if(c.getText().equals("F"))
        	{
        		FCheck.setEnabled(true);
        	}
        	else if(c.getText().equals("MSFT"))
        	{
        		MSFTCheck.setEnabled(true);
        	}
        	else if(c.getText().equals("YHOO"))
        	{
        		YHOOCheck.setEnabled(true);
        	}
        	
            cp.addStock(c.getText().toLowerCase());
            this.updateUI();
        }
        
        
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainGame());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
