package am.aca.wftartproject.model;

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

    public void setSpecialization(ArtistSpecialization specialization) {
        this.specialization = specialization;
    }

    public byte[] getArtistPhoto() {
        return artistPhoto;
    }

    public void setArtistPhoto(byte[] artistPhoto) {
        this.artistPhoto = artistPhoto;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
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
                "user=" +
                ", specialization=" + specialization +
                ", itemList=" + itemList +
                '}';
    }

    public boolean isValidArtist(){
        return
                super.isValidUser() &&
                specialization != null;
    }
}
