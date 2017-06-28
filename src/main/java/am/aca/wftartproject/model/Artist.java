package am.aca.wftartproject.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ASUS on 24-May-17
 */
public class Artist extends AbstractUser {

    private ArtistSpecialization specialization;
    private byte[] artistPhoto;
    private List<Item> itemList;
    private String base64;


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

    public String getBase64() {
        return base64;
    }

    public Artist setBase64(String base64) {
        this.base64 = base64;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                ", artistPhoto=" + Arrays.toString(artistPhoto) +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", shoppingCard=" + shoppingCard +
                ", itemList=" + itemList +
                '}';
    }

    public boolean isValidArtist() {
        return super.isValidUser() ;
    }
}
