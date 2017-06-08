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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", artistPhoto=" + Arrays.toString(artistPhoto) +
                ", shoppingCard=" + shoppingCard +
                ", itemList=" + itemList +
                '}';
    }

    public boolean isValidArtist() {
        return super.isValidUser() &&
                specialization != null;
    }
}
