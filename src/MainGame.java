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
import java.util.Hashtable;


public class MainGame extends JPanel implements ActionListener, ItemListener, ChangeListener, ListSelectionListener {

    //Define our global scale variables
	static ArrayList<JLabel> stockAL = new ArrayList<JLabel>();
	static ArrayList<String> AmountList = new ArrayList<String>();
	JButton playButton,
			BuyButton,
			SellButton;
	public JLabel stockPriceLabel;


    public static int movingAverageSelected = 0;
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
    
    JSlider speedSldr;
    private int speedMin = 50;
    private int speedMax = 300;
    private int speedStart = 150;
    private int speedIncrement = 50;

    ArrayList<JCheckBox> MainGamestocks;

    int Speed = speedStart;
    
    JCheckBoxList cbList = new JCheckBoxList();

    public MainGame() throws SQLException, ClassNotFoundException, ParseException {
        //All this is just layout stuff and declaring the event listener
        setLayout(null);

        // set background colour
        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(1100, 630));

        JLabel lblChartTypes = new JLabel("Chart Types:");
        lblChartTypes.setBounds(15, 10, 100, 14);
        add(lblChartTypes);

        JLabel lblStockInformation = new JLabel("Stock Information:");
        lblStockInformation.setBounds(15, 70, 120, 14);
        add(lblStockInformation);

        MainGamestocks = new ArrayList<JCheckBox>();

        MainGamestocks.addAll(Arrays.asList(new JCheckBox[]{appl, bac, coke, cop, cost, dis, f, msft, nke, yhoo}));

        cbList.setListData(MainGamestocks.toArray(new JCheckBox[MainGamestocks.size()]));

        JScrollPane jsp = new JScrollPane(cbList);
        jsp.setBounds(10, 90, 120, 150);
        add(jsp);

        playButton = new JButton("Play");
        playButton.setEnabled(false);
        playButton.addActionListener(this);
        playButton.setBounds(10, 300, 100, 30);
        add(playButton);
        
        BuyButton = new JButton("Buy");
        BuyButton.addActionListener(this);
        BuyButton.setBounds(10, 340, 100, 30);
        add(BuyButton);
        BuyButton.setEnabled(false);
        
        SellButton = new JButton("Sell");
        SellButton.addActionListener(this);
        SellButton.setBounds(10, 380, 100, 30);
        add(SellButton);
        SellButton.setEnabled(false);
        
        movingAverageLbl = new JLabel("Moving Average Period:");
        movingAverageLbl.setBounds(130, 10, 150, 20);
        add(movingAverageLbl);
        
        movingAverageCB = new JComboBox<Integer>();
        movingAverageCB.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {10,25,50,100}));
        movingAverageCB.setToolTipText("");
        movingAverageCB.setBounds(150, 35, 100, 20);
        add(movingAverageCB);
        movingAverageCB.addActionListener(this);
        movingAverageCB.setSelectedIndex(0);


        stockPriceLabel = new JLabel("Name, shares, purchase, current");
        stockPriceLabel.setBounds(10, 420, 200, 30);
        add(stockPriceLabel);


        // purchase stuff
        purchasesJList = new JList<Purchase>();
        model = new DefaultListModel();
        purchasesJList.setModel(model);
        purchasesJList.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(purchasesJList);
        scrollPane.setBounds(10, 450, 150, 150);
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
        
        Hashtable speedTable = new Hashtable();
        speedTable.put(new Integer(speedMin), new JLabel("Fast"));
        speedTable.put(new Integer(speedMax), new JLabel("Slow"));
        speedSldr = new JSlider(JSlider.HORIZONTAL, speedMin, speedMax, speedStart);
        speedSldr.setMajorTickSpacing(speedIncrement);
        speedSldr.setLabelTable(speedTable);
        speedSldr.setInverted(true);
        speedSldr.setPaintTicks(true);
        speedSldr.setPaintLabels(true);
        speedSldr.setBounds(10, 240, 150, 50);
        add(speedSldr);
        speedSldr.addChangeListener(this);
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

    	else if(e.getSource() == movingAverageCB) {
    		if (movingAverageCB.getSelectedIndex() == 0){
    			movingAverageSelected = 10;
    		}
    		else if (movingAverageCB.getSelectedIndex() == 1){
    			movingAverageSelected = 25;
    		}
    		else if (movingAverageCB.getSelectedIndex() == 2){
    			movingAverageSelected = 50;
    		}
    		else if (movingAverageCB.getSelectedIndex() == 3){
    			movingAverageSelected = 100;
    		}
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
		if (!source.getValueIsAdjusting()) {
			Speed = (int)source.getValue();
			if (cp.isRunning()) {
				cp.pause();
				cp.play(Speed);
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
