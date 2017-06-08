package am.aca.wftartproject.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistory {

    private Long userId;
    private Long itemId;
    private Timestamp purchaseDate;

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

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public PurchaseHistory() {
    }

    public PurchaseHistory(Timestamp purchaseDate) {
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

    public boolean isValidPurchaseHistroy(){
        return
                userId != null &&
                userId > 0 &&
                itemId != null &&
                itemId > 0 &&
                purchaseDate != null;
    }


}
