package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public class ShoppingCard {

    private Long id;
    private double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
