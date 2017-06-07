package am.aca.wftartproject.model;


import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by ASUS on 30-May-17
 */
public abstract class AbstractUser {

    Long id;
    String firstName;
    String lastName;
    int age;
    String email;
    String password;
    ShoppingCard shoppingCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCard getShoppingCard() {
        return shoppingCard;
    }

    public void setShoppingCard(ShoppingCard shoppingCard) {
        this.shoppingCard = shoppingCard;
    }

    public boolean isValidUser() {

        return
                id != null &&
                id > 0 &&
                !isEmptyString(firstName) &&
                !isEmptyString(lastName) &&
                age > 0 && age < 150 &&
                !isEmptyString(email) &&
                !isEmptyString(password) &&
                shoppingCard.isValidShoppingCard();
    }
}
