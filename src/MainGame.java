//import javafx.scene.control.Tooltip;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;


public class MainGame extends JPanel implements ActionListener, ItemListener, ChangeListener, ListSelectionListener {

    //Define our global scale variables
	static ArrayList<JLabel> stockAL = new ArrayList<JLabel>();
	static ArrayList<String> AmountList = new ArrayList<String>();
	static JButton playButton,
			BuyButton,
			SellButton;
	public JLabel stockPriceLabel;


    public static int movingAverageSelected = 25;
    JLabel movingAverageLbl;
    JComboBox<Integer> movingAverageCB;
    ChartPanel cp;

    //Purchase stuff
    JList<Purchase> purchasesJList;
    DefaultListModel model;
    Purchase currentSelectedPurchase;

    
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

    static JLabel BankDisplayLabel = new JLabel("$10000");
    static double StatusAmount = 0;
    static double BankAmount = 10000;
    
    JCheckBox appl = StockCheckBox("AAPL");
    JCheckBox bac  = StockCheckBox("BAC");
    JCheckBox coke = StockCheckBox("COKE");
    JCheckBox cop  = StockCheckBox("COP");
    JCheckBox cost = StockCheckBox("COST");
    JCheckBox dis  = StockCheckBox("DIS");
    JCheckBox f    = StockCheckBox("F");
    JCheckBox msft = StockCheckBox("MSFT");
    JCheckBox nke  = StockCheckBox("NKE");
    JCheckBox yhoo = StockCheckBox("YHOO");
    
    JLabel speedLbl;
    JSlider speedSldr;
    private int speedMin = 50;
    private int speedMax = 300;
    private int speedStart = 150;
    private int speedIncrement = 50;
    
    JSlider movingAverageSldr;
    private int movingAverageMin = 10;
    private int movingAverageMax = 100;
    private int movingAverageStart = 25;
    private int movingAverageIncrement = 30;

    ArrayList<JCheckBox> MainGamestocks;

    int Speed = speedStart;
    
    JCheckBoxList cbList = new JCheckBoxList();
    
	//////////////////////NEW//////////////////////////////
	static ArrayList<Date> FakeDates= new ArrayList<Date>();
	static ArrayList<Double> FakeDouble= new ArrayList<Double>();
	static final int SlideMIN = 0;
	static final int SlideMAX = 50;
	static final int SlideINIT = 30; 
	JSlider MovingWindowsSlider = new JSlider(JSlider.HORIZONTAL,
            SlideMIN, SlideMAX, SlideINIT);
	Stock stockV1;
	///////////////////////END////////////////////////////

    public MainGame() throws SQLException, ClassNotFoundException, ParseException {
        //All this is just layout stuff and declaring the event listener
        setLayout(null);
       
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // set background colour
        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(1200, 720));

        /*
        JLabel lblChartTypes = new JLabel("Chart Types:");
        lblChartTypes.setBounds(15, 10, 100, 14);
        add(lblChartTypes);
		*/

        JLabel lblStockInformation = new JLabel("Stock Information:");
        add(lblStockInformation);

        MainGamestocks = new ArrayList<JCheckBox>();

        MainGamestocks.addAll(Arrays.asList(new JCheckBox[]{appl, bac, coke, cop, cost, dis, f, msft, nke, yhoo}));

        cbList.setListData(MainGamestocks.toArray(new JCheckBox[MainGamestocks.size()]));

        JScrollPane jsp = new JScrollPane(cbList);
        add(jsp);

        playButton = new JButton("Play");
        playButton.setEnabled(false);
        playButton.addActionListener(this);
        
        add(playButton);
        
        BuyButton = new JButton("Buy");
        BuyButton.addActionListener(this);
        
        BuyButton.setBackground(Color.GREEN);
        add(BuyButton);
        BuyButton.setEnabled(false);
        
        SellButton = new JButton("Sell");
        SellButton.addActionListener(this);
        
        SellButton.setBackground(Color.RED);
        add(SellButton);
        SellButton.setEnabled(false);
        
        movingAverageLbl = new JLabel("Moving Average Period:", SwingConstants.CENTER);
        
        add(movingAverageLbl);
        
        movingAverageSldr = new JSlider(JSlider.HORIZONTAL, movingAverageMin, movingAverageMax, movingAverageStart);
        movingAverageSldr.setMajorTickSpacing(movingAverageIncrement);
        movingAverageSldr.setLabelTable(movingAverageSldr.createStandardLabels(movingAverageIncrement));
        movingAverageSldr.setPaintTicks(true);
        movingAverageSldr.setPaintLabels(true);
        add(movingAverageSldr);
        movingAverageSldr.addChangeListener(this);
        


        stockPriceLabel = new JLabel("Name, shares, purchase, current");
        stockPriceLabel.setBounds(10, 420, 200, 30);
        add(stockPriceLabel);


        // purchase stuff
        purchasesJList = new JList<Purchase>();
        model = new DefaultListModel();
        purchasesJList.setModel(model);
        purchasesJList.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(purchasesJList);
        
        purchasesJList.setVisibleRowCount(10);
        purchasesJList.setFixedCellHeight(15);
        purchasesJList.setFixedCellWidth(100);
        add(scrollPane);

        /*
        for (int i = 0; i < 10; i++) {
        	stockAL.add(stockPriceLabel = new JLabel());
        	stockAL.get(i).setBounds(10, (440 + (i * 20)), 200, 30);
        	add(stockAL.get(i));
        }
        */

        cp = new ChartPanel(this);
        
        add(cp);
        
        /*
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
        */
        
        speedLbl = new JLabel("Play speed:", SwingConstants.CENTER);
        add(speedLbl);
        
        Hashtable speedTable = new Hashtable();
        speedTable.put(new Integer(speedMin), new JLabel("Fast"));
        speedTable.put(new Integer(speedMax), new JLabel("Slow"));
        speedSldr = new JSlider(JSlider.HORIZONTAL, speedMin, speedMax, speedStart);
        speedSldr.setMajorTickSpacing(speedIncrement);
        speedSldr.setLabelTable(speedTable);
        speedSldr.setInverted(true);
        speedSldr.setPaintTicks(true);
        speedSldr.setPaintLabels(true);
        add(speedSldr);
        speedSldr.addChangeListener(this);
        
        BankDisplayLabel.setVerticalAlignment(SwingConstants.TOP);
        
        add(BankDisplayLabel);
        
        JLabel lblBank = new JLabel("Bank:");
        
        add(lblBank);
        
    	/////////////NEW///////////////////////////
    	//Add fake data to get out of errors
    	for(int i=0;i<=15;i++){
    		Calendar cal = Calendar.getInstance();
    		cal.set(2011, 5+i, 10);
    		FakeDates.add(cal.getTime());
    		FakeDouble.add(i*2.3);
    	}
    	//new instance of stock class to access its methods
    	stockV1 = new Stock("BLAH",FakeDates,FakeDouble);
    	stockV1.UserChooseMovingDate(30);
    	//Setup slider
    	MovingWindowsSlider.setMinorTickSpacing(5);
    	MovingWindowsSlider.setMajorTickSpacing(10);
    	MovingWindowsSlider.setPaintTicks(true);
    	MovingWindowsSlider.setPaintLabels(true);
    	MovingWindowsSlider.setLabelTable(MovingWindowsSlider.createStandardLabels(10));
    	
    	MovingWindowsSlider.addChangeListener(new ChangeListener(){
    		@Override
    		public void stateChanged( ChangeEvent event )
    	{
    		
    		if( event.getSource() == MovingWindowsSlider);
    		{
    			stockV1.UserChooseMovingDate(MovingWindowsSlider.getValue());
    			
    		}
    	}
    	});;
    	add(MovingWindowsSlider);
    	JLabel MovingWindowlbl = new JLabel("Moving Window Timeframe:", SwingConstants.CENTER);
        
        add(MovingWindowlbl);
    	/////////////////////////////END/////////////////////////////
        // X loc, Y loc, width, height
        cp.setBounds(250, 0, 980, 700);
        
        lblStockInformation.setBounds(15, 15, 120, 15);
        jsp.setBounds(15, 40, 100, 200);
        stockPriceLabel.setBounds(130, 15, 150, 20);
        scrollPane.setBounds(130, 40, 110, 200);
        
        playButton.setBounds(15, 270, 100, 30);
        BuyButton.setBounds(15, 310, 100, 30);
        SellButton.setBounds(15, 350, 100, 30);
        
        lblBank.setBounds(180, 280, 100, 20);
        BankDisplayLabel.setBounds(170, 300, 74, 150);
        
        speedLbl.setBounds(15, 410, 200, 20);
        speedSldr.setBounds(15, 430, 200, 50);
        
        movingAverageLbl.setBounds(15, 510, 200, 20);
        movingAverageSldr.setBounds(15, 530, 200, 50);
        
        MovingWindowlbl.setBounds(15, 610, 200, 20);
        MovingWindowsSlider.setBounds(15, 630, 200, 50);
        
    }

    private JCheckBox StockCheckBox(String s){
        JCheckBox check = new JCheckBox(s);
        check.addItemListener(this);

        return check;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource() == playButton) {
    		if(e.getActionCommand().equals("Play")) {
                ArrayList<JCheckBox> toRemove = new ArrayList<JCheckBox>();
                for (JCheckBox s : MainGamestocks) {
                    if (s.isSelected()) {
                        cp.addStock(s.getText().toLowerCase());
                        s.setSelected(false);
                    } else {
                        toRemove.add(s);
                    }
                }

                for (JCheckBox r : toRemove) {
                    MainGamestocks.remove(r);
                }

                cbList.setListData(MainGamestocks.toArray(new JCheckBox[MainGamestocks.size()]));
                cbList.updateUI();

                playButton.setText("Pause");
                BuyButton.setEnabled(true);
                cp.play(Speed);
            } else if (e.getActionCommand().equals("Pause")) {
                cp.pause();
    			playButton.setText("Resume");
    		} else if (e.getActionCommand().equals("Resume")) {
                cp.play(Speed);
            	playButton.setText("Pause");
    		}
    	}
    	else if(e.getSource() == BuyButton) {

            for (JCheckBox s : MainGamestocks) {
                if (s.isSelected()) {
                    Stock s1 = cp.db.readFromDb(s.getText().toLowerCase());
                    Purchase p = new Purchase(s1, 1);
                    model.addElement(p);
                }
            }
        }
    	else if(e.getSource() == SellButton) {
            currentSelectedPurchase.sell();
        }

    }

    public void update() {
        purchasesJList.updateUI();
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox c = (JCheckBox) e.getSource();

        if (!cp.running) {
            int num_selected = 0;

            for (JCheckBox s : MainGamestocks) {
                if (s.isSelected()) num_selected++;
            }
            if (num_selected >= 5) {
                c.setSelected(false);
                JOptionPane.showMessageDialog(null,"select a maximum of 4");
                return;
            }
        } else {
            return;
        }

        AmountList = new ArrayList<String>();
        if(c.isSelected()){
        	playButton.setEnabled(true);
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
            this.updateUI();
        }
    }
    
    @Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if(e.getSource() == movingAverageSldr){
			if (!source.getValueIsAdjusting()) {
				movingAverageSelected = (int)source.getValue();
			}
		}
		else if (e.getSource() == speedSldr){
			if(!source.getValueIsAdjusting()) {
				Speed = (int)source.getValue();
				if (cp.isRunning()) {
					cp.pause();
					cp.play(Speed);
				}
			}
		}
	}

    @Override
    public void valueChanged(ListSelectionEvent e) {
        currentSelectedPurchase = purchasesJList.getSelectedValue();
        SellButton.setEnabled(true);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainGame());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
