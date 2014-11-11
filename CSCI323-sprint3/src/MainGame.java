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
	JButton NewWindowButton,
			BuyButton,
			SellButton;
	public JLabel stockPriceLabel;
    JComboBox ChartTypeCB = new JComboBox();
    public static String CurrentGraph = "";
    ChartPanel cp;

    public MainGame() throws SQLException, ClassNotFoundException {
        //All this is just layout stuff and declaring the event listener
        setLayout(null);

        setPreferredSize(new Dimension(1000,630));

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

        JCheckBoxList cbList = new JCheckBoxList();
        JCheckBox[] stocks = {StockCheckBox("AAPL"), StockCheckBox("BAC"), StockCheckBox("COKE"),
                StockCheckBox("COP"), StockCheckBox("COST"), StockCheckBox("DIS"), StockCheckBox("F"),
                StockCheckBox("MSFT"), StockCheckBox("NKE"), StockCheckBox("YHOO")};
        cbList.setListData(stocks);

        JScrollPane jsp = new JScrollPane(cbList);
        jsp.setBounds(10, 90, 120, 150);
        add(jsp);

        NewWindowButton = new JButton("play");
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
        stockPriceLabel.setBounds(10, 390, 200, 30);
        add(stockPriceLabel);
        
        for (int i = 0; i < 10; i++) {
        	stockAL.add(stockPriceLabel = new JLabel());
        	stockAL.get(i).setBounds(10, (420 + (i * 20)), 200, 30);
        	add(stockAL.get(i));
        }


        cp = new ChartPanel();
        cp.setBounds(180,10, 800, 600);
        add(cp);

    }

    private JCheckBox StockCheckBox(String s){
        JCheckBox check = new JCheckBox(s);
        check.addItemListener(this);
        return check;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == NewWindowButton) {
    		cp.play();
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
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox c = (JCheckBox) e.getSource();
        if(c.isSelected()){
            cp.addStock(c.getText().toLowerCase());
            this.updateUI();
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Create and set up the window.
        JFrame frame = new JFrame("Stock Market Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new MainGame());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
