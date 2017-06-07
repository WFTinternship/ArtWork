package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public class Item {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private Double price;
    private Boolean status;
    private ItemType itemType;

    public Long getId() {
        return id;
    }

    public Item setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Item setPhotoURL(String photo) {
        this.photoURL = photo;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Item setPrice(double price) {
        this.price = price;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public Item setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Item setItemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public Item() {

    }

    public Item(String title, String description, String photoURL, double price, boolean status, ItemType itemType) {
        this.title = title;
        this.description = description;
        this.photoURL = photoURL;
        this.price = price;
        this.status = status;
        this.itemType = itemType;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photoURL=" + photoURL +
                ", price=" + price +
                ", status=" + status +
                ", itemType=" + itemType +
                '}';
    }
}
