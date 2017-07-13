package am.aca.wftartproject.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by ASUS on 24-May-17
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 30)
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
    @Column(name = "description", nullable = false)
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photoURL;
    @Column(name = "price", nullable = false, length = 10)
    private Double price;
    @Column(nullable = false)
    private Boolean status;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    @Column(name = "addition_date", nullable = false)
    private java.util.Date additionDate;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "purchaseItem", cascade = CascadeType.ALL)
    private PurchaseHistory purchaseHistory;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

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

    public java.util.Date getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(java.util.Date additionDate) {
        this.additionDate = additionDate;
    }

    public Item() {

    }

    public Item(String title, String description, List<String> photoURL, Double price, Long artistId, Boolean status, ItemType itemType, Date additionDate) {
        this.title = title;
        this.description = description;
        this.photoURL = photoURL;
        this.price = price;
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
                ", status=" + status +
                ", itemType=" + itemType +
                ", additionDate=" + additionDate +
                '}';
    }

    public boolean isValidItem() {
//        return id != null &&
//                id > 0 &&
        return
                !isEmptyString(title) &&
                        !isEmptyString(photoURL.get(0)) &&
                        artist != null &&
                        price != 0 &&
                        itemType != null;
        //        && additionDate != null;

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
        if (!getStatus().equals(item.getStatus())) return false;
        if (getItemType() != item.getItemType()) return false;
        return getAdditionDate().getDate() / 100000000 == item.getAdditionDate().getDate() / 100000000;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + getItemType().hashCode();
        return result;
    }

}
