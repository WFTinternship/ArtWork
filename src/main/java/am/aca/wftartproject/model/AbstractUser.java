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
    String userPasswordRepeat;
    ShoppingCard shoppingCard;

    public String getUserPasswordRepeat() {
        return userPasswordRepeat;
    }

    public AbstractUser setUserPasswordRepeat(String userPasswordRepeat) {
        this.userPasswordRepeat = userPasswordRepeat;
        return this;
    }


    public Long getId() {
        return id;
    }
    public AbstractUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AbstractUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AbstractUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public AbstractUser setAge(int age) {
        this.age = age;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AbstractUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AbstractUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public ShoppingCard getShoppingCard() {
        return shoppingCard;
    }

    public AbstractUser setShoppingCard(ShoppingCard shoppingCard) {
        this.shoppingCard = shoppingCard;
        return this;
    }

    public boolean isValidUser() {
        return
//                id != null &&
//                id > 0 &&
                !isEmptyString(firstName) &&
                !isEmptyString(lastName) &&
                age > 0 && age < 150 &&
                !isEmptyString(email) &&
                !isEmptyString(password);
           //     password.equals(userPasswordRepeat);
//                &&
//                shoppingCard.isValidShoppingCard();
    }
}
