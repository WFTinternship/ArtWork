package am.aca.wftartproject.entity;

import javax.persistence.*;

/**
 * Created by ASUS on 24-May-17
 */
@Entity
public class ShoppingCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long buyer_id;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShoppingCardType shoppingCardType;

    public Long getId() {
        return id;
    }

    public Long getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Long buyer_id) {
        this.buyer_id = buyer_id;
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
        return (/*id != null && */balance != 0 && shoppingCardType != null);
    }
}
