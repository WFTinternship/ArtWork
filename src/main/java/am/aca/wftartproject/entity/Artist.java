package am.aca.wftartproject.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ASUS on 24-May-17
 */
@Entity
@DynamicUpdate
@Table(name = "artist")
public class Artist extends AbstractUser implements Serializable, Cloneable {

    @Enumerated(EnumType.STRING)
    private ArtistSpecialization specialization;
    @Column(length = 10000000, name = "photo")
    private byte[] artistPhoto = null;
    @OneToMany(targetEntity = am.aca.wftartproject.entity.Item.class, cascade = CascadeType.ALL, mappedBy = "artist")
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


    public Artist() {

    }

    public Artist(ArtistSpecialization specialization, byte[] artistPhoto, List<Item> itemList) {
        this.specialization = specialization;
        this.artistPhoto = artistPhoto;
        this.itemList = itemList;
    }

    public boolean isValidArtist() {
        return super.isValidUser();
    }

    public boolean equals(Artist other) {
        return (other.firstName.equals(this.firstName) && other.lastName.equals(this.lastName) && other.age == this.age && other.password.equals(this.password) && other.specialization.equals(this.specialization) && Arrays.equals(this.artistPhoto, other.artistPhoto));
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", firstName='" + firstName + '\'' +
                ", artistPhoto=" + Arrays.toString(artistPhoto) +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userPasswordRepeat='" + userPasswordRepeat + '\'' +
                '}';
    }
}
