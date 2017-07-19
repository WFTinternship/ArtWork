package am.aca.wftartproject.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ASUS on 27-May-17
 */
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private AbstractUser absUser;
    @Column(nullable = false)
    private Date purchase_date;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    private Item purchaseItem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return purchaseItem;
    }

    public PurchaseHistory setItem(Item item) {
        this.purchaseItem = item;
        return this;
    }


    public Date getPurchaseDate() {
        return purchase_date;
    }

    public PurchaseHistory setPurchaseDate(Date purchaseDate) {
        this.purchase_date = purchaseDate;
        return this;
    }

    public PurchaseHistory() {
    }

    public PurchaseHistory(Long userId, Long itemId) {

    }

    public AbstractUser getAbsUser() {
        return absUser;
    }

    public PurchaseHistory setAbsUser(AbstractUser absUser) {
        this.absUser = absUser;
        return this;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Item getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(Item purchaseItem) {
        this.purchaseItem = purchaseItem;
    }


    @Override
    public String toString() {
        return "PurchaseHistory{" +
                "user=" + absUser +
                ", item=" + purchaseItem +
                ", purchaseDate=" + purchase_date +
                '}';
    }

    public boolean isValidPurchaseHistory() {
        return absUser != null &&
                purchaseItem != null &&
                purchase_date != null;
    }


}