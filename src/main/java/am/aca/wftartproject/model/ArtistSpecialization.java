package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public enum ArtistSpecialization {

    PAINTER(1, "PAINTER"),
    SCULPTOR(2, "SCULPTOR"),
    PHOTOGRAPHER(3, "PHOTOGRAPHER"),
    OTHER(4, "OTHER");

    private final int id;
    private final String type;

    ArtistSpecialization(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public int getSpecId() {
        return ArtistSpecialization.valueOf(type).getId();
    }
}
