package am.aca.wftartproject.model;

/**
 * Created by ASUS on 29-May-17
 */
public enum ItemType {

    SCULPTURE(1, "SCULPTURE"),
    PHOTOGRAPHY(2, "PHOTOGRAPHY"),
    PAINTING(3, "PAINTING"),
    OTHER(4, "OTHER");


    private final int typeId;
    private final String type;

    ItemType(int itemId, String type) {
        this.typeId = itemId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public static int getIdByType(String type) {
        return ItemType.valueOf(type).getTypeId();
    }

    public static ItemType getItemType(String type) {
        return ItemType.valueOf(type);
    }
}
