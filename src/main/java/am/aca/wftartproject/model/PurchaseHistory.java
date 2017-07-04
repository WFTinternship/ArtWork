package am.aca.wftartproject.model;

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

    @Column(name = "buyer_id")
    private Long userId;
    private Long item_id;
    private Date purchase_date;
    @Column(length = 1000000)
    private Item item;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Item getItem() {
        return item;
    }

    public PurchaseHistory setItem(Item item) {
        this.item = item;
        return this;
    }


    public Long getUserId() {
        return userId;
    }

    public PurchaseHistory setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getItemId() {
        return item_id;
    }

    public PurchaseHistory setItemId(Long itemId) {
        this.item_id = itemId;
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
        this.userId = userId;
        this.item_id = itemId;
    }


    @Override
    public String toString() {
        return "PurchaseHistory{" +
                "userId=" + userId +
                ", itemId=" + item_id +
                ", purchaseDate=" + purchase_date +
                '}';
    }

    public boolean isValidPurchaseHistory() {
        return userId != null &&
                userId > 0 &&
                item_id != null &&
                item_id > 0 &&
                purchase_date != null;
    }


}