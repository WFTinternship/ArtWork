package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17.
 */
public class Item {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private double price;
    private boolean status;
    private ItemType itemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photo) {
        this.photoURL = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
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
                ", itemType=" + itemType.toString() +
                '}';
    }
}
