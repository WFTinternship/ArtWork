package am.aca.wftartproject.entity;

/**
 * Created by ASUS on 23-Jun-17
 */
public enum ShoppingCardType {

    PAYPAL(1, "PAYPAL"),
    MASTERCARD(2, "MASTERCARD");

    final int id;
    final String type;

    ShoppingCardType(int id, String type) {
        this.id = id;
        this.type = type;
    }


    public String getType() {
        return type;
    }


}
