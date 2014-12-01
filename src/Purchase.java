import javax.swing.*;

/**
 * Created by aaron on 11/30/14.
 */
public class Purchase extends JLabel {
    Stock s;
    double purchasePrice;
    double sellPrice;

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
}
