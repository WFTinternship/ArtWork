package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public class ShoppingCard {

    private Long id;
    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public ShoppingCard() {
    }

    public ShoppingCard(Double balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "ShoppingCard{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }

    public boolean isValidShoppingCard() {
        return (id != null && balance != null);
    }
}
