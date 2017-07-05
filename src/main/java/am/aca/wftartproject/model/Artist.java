package am.aca.wftartproject.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ASUS on 24-May-17
 */
@Entity
@Table(name = "artist")
public class Artist extends AbstractUser implements Serializable {

    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private Integer userId;

//    @Column(name = "spec_id", nullable = false)
//    private Integer specId;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "spec_id")
    private ArtistSpecialization specialization;

    @Lob
    @Column(length=10000000,name = "photo", nullable = false)
    private byte[] artistPhoto;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Item> itemList;

    @Transient
    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public ArtistSpecialization getSpecialization() {
        return specialization;
    }

    public Artist setSpecialization(ArtistSpecialization specialization) {
        this.specialization = specialization;
        return this;
    }

    public byte[] getArtistPhoto() {
        return artistPhoto;
    }

    public Artist setArtistPhoto(byte[] artistPhoto) {
        this.artistPhoto = artistPhoto;
        return this;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public Artist setItemList(List<Item> itemList) {
        this.itemList = itemList;
        return this;
    }

    public Artist() {

    }

    public Artist(ArtistSpecialization specialization, byte[] artistPhoto, List<Item> itemList) {
        this.specialization = specialization;
        this.artistPhoto = artistPhoto;
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", firstName='" + firstName + '\'' +
                ", artistPhoto=" + Arrays.toString(artistPhoto) +
                ", lastName='" + lastName + '\'' +
                ", itemList=" + itemList +
                ", base64='" + base64 + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
//                ", userPasswordRepeat='" + userPasswordRepeat + '\'' +
                ", shoppingCard=" + shoppingCard +
                '}';
    }


    public boolean isValidArtist() {
        return super.isValidUser() ;
    }
}
