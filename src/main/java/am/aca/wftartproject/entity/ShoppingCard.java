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
    private double balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShoppingCardType shoppingCardType;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private AbstractUser abstractUser;

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

    public AbstractUser getAbstractUser() {
        return abstractUser;
    }

    public void setAbstractUser(AbstractUser abstractUser) {
        this.abstractUser = abstractUser;
    }

    public boolean isValidShoppingCard() {
        return (/*id != null && */this.balance != 0 && this.shoppingCardType != null && this.abstractUser != null);
    }
}
