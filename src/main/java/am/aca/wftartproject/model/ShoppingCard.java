package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public class ShoppingCard {

    private Long id;
    private Long buyerId;
    private double balance;
    private ShoppingCardType shoppingCardType;

    public Long getId() {
        return id;
    }

    public ShoppingCard setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public ShoppingCard setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public ShoppingCard setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public ShoppingCardType getShoppingCardType() {
        return shoppingCardType;
    }

    public ShoppingCard setShoppingCardType(ShoppingCardType shoppingCardType) {
        this.shoppingCardType = shoppingCardType;
        return this;
    }

    public ShoppingCard() {
    }

    public ShoppingCard(double balance, ShoppingCardType shoppingCardType) {
        this.balance = balance;
        this.shoppingCardType = shoppingCardType;
    }

    @Override
    public String toString() {
        return "ShoppingCard{" +
                "id=" + id +
                ", balance=" + balance +
                ", shoppingCardType=" + shoppingCardType +
                '}';
    }

    public boolean isValidShoppingCard() {
        return (shoppingCardType != null);
    }
}
