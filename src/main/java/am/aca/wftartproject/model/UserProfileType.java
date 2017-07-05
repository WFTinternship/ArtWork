package am.aca.wftartproject.model;

import java.io.Serializable;

/**
 * Created by ASUS on 05-Jul-17
 */
public enum UserProfileType implements Serializable {

    USER("USER"),
    ARTIST("ARTIST");


    private String type;

    UserProfileType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
