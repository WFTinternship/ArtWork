package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17.
 */
public class ShoppingCard {

    private int id;
    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public ShoppingCard() {
    }

    public ShoppingCard(double balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "ShoppingCard{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
