package am.aca.wftartproject.model;

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

    @Basic
    private String title;

    @Basic
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photoURL;

    @Basic
    private Double price;

    @JoinColumn(name = "artist_id")
    private Long artistId;

    @Basic
    private Boolean status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;


//    @Temporal(TemporalType.DATE)
    @Column(name = "addition_date")
    private Date additionDate;


    public Long getArtist_id() {
        return artistId;
    }

    public void setArtist_id(Long artist_id) {
        this.artistId = artist_id;
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

    public Date getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(Date additionDate) {
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
                ", artistId ="  +
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
                /*artistId != null &&
                artistId > 0 &&*/
                price != 0 &&
                itemType != null ;
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
