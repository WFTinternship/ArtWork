package am.aca.wftartproject.util;

/**
 * Created by ASUS on 21-Jun-17
 */
public enum SpringBeanType {

    USERSERVICE("userService"),
    ARTISTSERVICE("artistService"),
    ITEMSERVICE("itemService"),
    SHOPPINGCARDSERVICE("shoppingCardService"),
    PURCHUSEHISTORYSERVICE("purchaseHistoryService"),
    ARTISTSPECIALIZATIONSERVICE("artistSpecializationService");


    private String type;

    SpringBeanType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }
}
