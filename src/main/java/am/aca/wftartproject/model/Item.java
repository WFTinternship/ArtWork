package am.aca.wftartproject.model;

import java.sql.Timestamp;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by ASUS on 24-May-17
 */
public class Item {

    private Long id;
    private String title;
    private String description;
    private String photoURL;
    private Double price;
    private Long artistId;
    private Boolean status;
    private ItemType itemType;
    private Timestamp additionDate;



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

    public Boolean getStatus() {
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

    public Timestamp getAdditionDate() {
        return additionDate;
    }

    public Item setAdditionDate(Timestamp additionDate) {
        this.additionDate = additionDate;
        return this;
    }

    public Item() {

    }

    public Item(String title, String description, String photoURL, Double price, Long artistId, Boolean status, ItemType itemType, Timestamp additionDate) {
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
        return id != null &&
                id > 0 &&
                !isEmptyString(title) &&
                !isEmptyString(photoURL) &&
                artistId != null &&
                artistId > 0 &&
                price != 0 &&
                itemType != null &&
                additionDate != null;

    }




}
