package am.aca.wftartproject.model;

import java.time.LocalDateTime;
import java.util.List;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.util.DateHelper.dateComparison;

/**
 * Created by ASUS on 24-May-17
 */
public class Item {

    private Long id;
    private String title;
    private String description;
    private List<String> photoURL;
    private Double price;
    private Long artistId;
    private Boolean status;
    private ItemType itemType;
    private LocalDateTime additionDate;

    public Boolean getStatus() {
        return status;
    }

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

    public List<String> getPhotoURL() {
        return photoURL;
    }

    public Item setPhotoURL(List<String> photo) {
        this.photoURL = photo;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Item setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public Item setArtistId(Long artistID) {
        this.artistId = artistID;
        return this;
    }

    public Boolean isStatus() {
        return status;
    }

    public Item setStatus(Boolean status) {
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

    public LocalDateTime getAdditionDate() {
        return additionDate;
    }

    public Item setAdditionDate(LocalDateTime additionDate) {
        this.additionDate = additionDate;
        return this;
    }

    public Item() {

    }

    public Item(String title, String description, List<String> photoURL, Double price, Long artistId, Boolean status, ItemType itemType, LocalDateTime additionDate) {
        this.title = title;
        this.description = description;
        this.photoURL = photoURL;
        this.price = price;
        this.artistId = artistId;
        this.status = status;
        this.itemType = itemType;
        this.additionDate = additionDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photoURL=" + photoURL +
                ", price=" + price +
                ", artistId =" + artistId +
                ", status=" + status +
                ", itemType=" + itemType +
                ", additionDate=" + additionDate +
                '}';
    }

    public boolean isValidItem() {
        return
                !isEmptyString(title) &&
                !isEmptyString(photoURL.get(0)) &&
                /*artistId != null &&
                artistId > 0 &&*/
                price != 0 &&
                itemType != null ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!getId().equals(item.getId())) return false;
        if (!getTitle().equals(item.getTitle())) return false;
        if (!getDescription().equals(item.getDescription())) return false;
        if (!getPhotoURL().equals(item.getPhotoURL())) return false;
        if (!getPrice().equals(item.getPrice())) return false;
        if (!getArtistId().equals(item.getArtistId())) return false;
        if (!getStatus().equals(item.getStatus())) return false;
        if (getItemType() != item.getItemType()) return false;
        return dateComparison(this.getAdditionDate(), item.getAdditionDate())/*getAdditionDate().equals(item.getAdditionDate())*/;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getPhotoURL().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getArtistId().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getItemType().hashCode();
        result = 31 * result + getAdditionDate().hashCode();
        return result;
    }
}
