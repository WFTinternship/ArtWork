package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public enum ArtistSpecialization {

    PAINTER(1, "painter"),
    SCULPTOR(2, "sculptor"),
    PHOTOGRAPHER(3, "photographer"),
    OTHER(4, "other");

    private final int specId;
    private final String specialization;

    ArtistSpecialization(int specId, String specialization) {
        this.specId = specId;
        this.specialization = specialization;
    }

    public int getSpecId() {
        return specId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public static int getIdBySpec(String specialization){
        return ArtistSpecialization.valueOf(specialization).getSpecId();
    }


}
