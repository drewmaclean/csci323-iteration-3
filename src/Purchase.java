import java.text.DecimalFormat;

import javax.swing.*;

/**
 * Created by aaron on 11/30/14.
 */
public class Purchase extends JLabel {
    Stock s;
    double purchasePrice;
    double sellPrice;
    
    double StatusPrice = 0;
    DecimalFormat myFormat = new DecimalFormat("#.00");

    public Purchase(Stock s) {
        this.s = s;
        purchasePrice = s.currentPrice;
    }

    public void sell() {
        sellPrice = s.currentPrice;
    }

    public String printCurrent() {
        return "" + s.currentPrice;
    }

    public void setText() {
        super.setText("current price = " + s.currentPrice);
    }

    public String toString() {

        return s.tickerSymbol + " $" + s.currentPrice + " :";
    }
    
    public void UpdateBankBuy(){
    	
    	MainGame.BankAmount -= purchasePrice;
    	MainGame.StatusAmount += purchasePrice;
    	
    	MainGame.BankDisplayLabel.setText("<html>$" + myFormat.format(MainGame.BankAmount) + "<br></br>$ -" + myFormat.format(MainGame.StatusAmount) + "</html>");
    }
    
    
    
    }

