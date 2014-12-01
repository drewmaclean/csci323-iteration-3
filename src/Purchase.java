import javax.swing.*;
import java.text.DecimalFormat;

/**
 * Created by aaron on 11/30/14.
 */
public class Purchase extends JLabel {
    Stock s;
    double purchasePrice;
    double sellPrice;
    int shares = 1;
    
    double StatusPrice = 0;
    DecimalFormat myFormat = new DecimalFormat("#.00");

    public Purchase(Stock s) {
        this.s = s;
        purchasePrice = s.currentPrice;
        s.buyPrice = purchasePrice;
        s.isOwned = true;
    }

    public void sell() {
        sellPrice = s.currentPrice;
        s.isOwned = false;
    }

    public void setText() {
        super.setText("current price = " + s.currentPrice);
    }

    public double profit() {
        if (s.isOwned) {
            return shares * (s.currentPrice - purchasePrice);
        } else {
            return shares * (sellPrice - purchasePrice);
        }
    }

    public String toString() {
        Double p = profit();
        String profit;
        if (p > 0) {
            profit = "<font color=\"green\">" + myFormat.format(p) + "</font>";
        } else {
            profit = "<font color=\"red\">" + myFormat.format(p) + "</font>";
        }
        return "<html>" + s.tickerSymbol + " :$" + profit + "</html>";
    }


    public void UpdateBankBuy(){
    	
    	MainGame.BankAmount -= purchasePrice;
    	
    	if(MainGame.BankAmount <= 0)
    	{
    		MainGame.BankDisplayLabel.setText("<html>GAME OVER!<br></br><br></br>You are out of money!</html>");
    		MainGame.BuyButton.setEnabled(false);
    		MainGame.SellButton.setEnabled(false);
    		MainGame.playButton.doClick();
    	}
    	else
    	{
    	MainGame.StatusAmount += purchasePrice;
    	
    	MainGame.BankDisplayLabel.setText("<html>$" + myFormat.format(MainGame.BankAmount) + "<br></br>$ -" + myFormat.format(MainGame.StatusAmount) + "</html>");
    	}
    }
}
