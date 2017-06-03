package am.aca.wftartproject.model;

/**
 * Created by ASUS on 29-May-17.
 */
public enum ItemType {

    SCULPTURE(1, "sculpture"),
    PHOTOGRAPHY(2, "photography"),
    PAINTING(3, "painting"),
    OTHER(4, "other");


    private final int typeId;
    private final String type;

    ItemType(int typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getTypeId(){
        return this.typeId;
    }

    public static int getIdByType(String type){
        return ItemType.valueOf(type).getTypeId();
    }

}
