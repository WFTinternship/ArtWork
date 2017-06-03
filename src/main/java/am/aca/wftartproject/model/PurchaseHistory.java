package am.aca.wftartproject.model;

import java.util.Date;

/**
 * Created by ASUS on 27-May-17.
 */
public class PurchaseHistory {

    private Long userId;
    private Long itemId;
    private Date purchaseDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public PurchaseHistory() {
    }

    public PurchaseHistory(Date purchaseDate) {
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


}
