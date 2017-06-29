package am.aca.wftartproject.model;

import java.util.Date;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistory {

    private Long userId;
    private Long itemId;
    private Date purchaseDate;
    private Item item;
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
        return itemId;
    }

    public PurchaseHistory setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public PurchaseHistory setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public PurchaseHistory() {
    }

    public PurchaseHistory(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }


    @Override
    public String toString() {
        return "PurchaseHistory{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                ", purchaseDate=" + purchaseDate +
                '}';
    }

    public boolean isValidPurchaseHistory() {
        return userId != null &&
                userId > 0 &&
                itemId != null &&
                itemId > 0 &&
                purchaseDate != null;
    }


}