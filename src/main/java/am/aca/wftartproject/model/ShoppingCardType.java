package am.aca.wftartproject.model;

/**
 * Created by ASUS on 23-Jun-17
 */
public enum ShoppingCardType {

    PAYPAL(1, "PAYPAL"),
    MASTERCARD(2, "MASTERCARD");

    private final int id;
    private final String type;

    ShoppingCardType(int id, String type) {
        this.id = id;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public int getTypeId() {
        return this.id;
    }

    public static int getIdByType(String type) {
        return ShoppingCardType.valueOf(type).getTypeId();
    }

    public static ShoppingCardType getItemType(String type) {
        return ShoppingCardType.valueOf(type);
    }


}
