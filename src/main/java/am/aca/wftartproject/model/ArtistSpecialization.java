package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */
public enum ArtistSpecialization {

    PAINTER(1),
    SCULPTOR(2),
    PHOTOGRAPHER(3),
    OTHER(4);


    private final int specValue;

    ArtistSpecialization(int specValue){
        this.specValue = specValue;
    }

    public int getSpecValue(){
        return this.specValue;
    }

    public String getSpecName(int specValue){
        return this.name();
    }

}
