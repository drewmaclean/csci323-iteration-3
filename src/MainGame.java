import javafx.scene.control.Tooltip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;


public class MainGame extends JPanel implements ActionListener, ItemListener {

    //Define our global scale variables
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

        JButton NewWindowButton = new JButton("play");
        NewWindowButton.addActionListener(this);
        NewWindowButton.setBounds(10, 279, 141, 62);
        add(NewWindowButton);


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
        cp.play();
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
