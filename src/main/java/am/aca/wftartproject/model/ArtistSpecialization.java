package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17.
 */
public enum ArtistSpecialization {

    PAINTER(1),
    SCULPTOR(2),
    PHOTOGRAPHER(3),
    OTHER(4);

    private int specId;

    ArtistSpecialization(int specId) {
        this.specId = specId;
    }

    public int getSpecId() {
        return specId;
    }

}
