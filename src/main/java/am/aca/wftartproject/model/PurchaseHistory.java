package am.aca.wftartproject.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistory {

    private Long userId;
    private Long itemId;
    private LocalDateTime purchaseDate;
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

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public PurchaseHistory setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public PurchaseHistory() {
    }

    public PurchaseHistory(Long userId, Long itemId, LocalDateTime purchaseDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.purchaseDate = purchaseDate;
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
