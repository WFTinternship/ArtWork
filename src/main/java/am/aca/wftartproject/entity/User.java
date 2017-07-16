package am.aca.wftartproject.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by ASUS on 24-May-17
 */
@Entity
@Table(name = "user")
public class User extends AbstractUser implements Serializable,Cloneable {

    public User() {
    }

    public User(String firstName, String lastName, int age, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, int age, String email, String password, String userPasswordRepeat, ShoppingCard shoppingCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
//        this.shoppingCard = shoppingCard;
        this.userPasswordRepeat = userPasswordRepeat;
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
//                ", shoppingCard='" + shoppingCard + '\'' +
                '}';
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
