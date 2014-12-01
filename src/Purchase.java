import javax.swing.*;
import java.text.DecimalFormat;

/**
 * Created by aaron on 11/30/14.
 */
public class Purchase extends JLabel {
    Stock s;
    double purchasePrice;
    double sellPrice;
    DecimalFormat df = new DecimalFormat("#.##");
    int shares;
    boolean sold = false;

    public Purchase(Stock s, int shares) {
        this.s = s;
        this.shares = shares;
        purchasePrice = s.currentPrice;
    }

    public void sell() {
        sellPrice = s.currentPrice;
    }

    public double profit() {
        if (sold) {
            return shares * (s.sellPrice - purchasePrice);
        } else {
            return shares * (s.currentPrice - purchasePrice);
        }
    }

    public String toString() {
        Double p = profit();
        String profit;
        if (p > 0) {
            profit = "<font color=\"green\">" + df.format(p) + "</font>";
        } else {
            profit = "<font color=\"red\">" + df.format(p) + "</font>";
        }
        return "<html>" + s.tickerSymbol + " " + shares + " $" + purchasePrice + " :$" + profit + "</html>";
    }
}
