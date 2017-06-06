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

    public ShoppingCard setId(Long id) {
        this.id = id;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public ShoppingCard setBalance(double balance) {
        this.balance = balance;
        return this;
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
