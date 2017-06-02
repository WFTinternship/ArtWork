package am.aca.wftartproject.model;

/**
 * Created by ASUS on 29-May-17.
 */
public enum ItemType {

    SCULPTURE(1),
    PHOTOGRATHY(2),
    PAINTING(3),
    OTHER(4);


    private final int itemValue;

    private ItemType(int itemValue) {
        this.itemValue = itemValue;
    }

    public int getItemValue(){
        return this.itemValue;
    }
}
