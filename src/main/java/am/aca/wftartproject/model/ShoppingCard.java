package am.aca.wftartproject.model;

import javax.persistence.*;

/**
 * Created by ASUS on 24-May-17
 */
@Entity
@Table(name="shopping_card")
public class ShoppingCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private double balance;

    @JoinColumn(name = "buyer_id")
    private Long buyerId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ShoppingCardType shoppingCardType;


    public Long getBuyer_id() {
        return buyerId;
    }

    public void setBuyer_id(Long buyer_id) {
        this.buyerId = buyer_id;
    }

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
