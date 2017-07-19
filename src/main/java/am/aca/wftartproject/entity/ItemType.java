package am.aca.wftartproject.entity;

/**
 * Created by ASUS on 29-May-17
 */
public enum ItemType {

    SCULPTURE(1, "SCULPTURE"),
    PHOTOGRAPHY(2, "PHOTOGRAPHY"),
    PAINTING(3, "PAINTING"),
    OTHER(4, "OTHER");


     final int typeId;
     final String type;

    ItemType(int typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
