package am.aca.wftartproject.model;

/**
 * Created by ASUS on 24-May-17
 */

public class User extends AbstractUser {

    public User() {
    }

    public User(String firstName, String lastName, int age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, int age, String email, String password, ShoppingCard shoppingCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.shoppingCard = shoppingCard;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", shoppingCard='" + shoppingCard + '\'' +
                '}';
    }
}
