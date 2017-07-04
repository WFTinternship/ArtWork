package am.aca.wftartproject.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import java.io.Serializable;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by ASUS on 30-May-17
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty
    @Column(name = "firstname", nullable = false, length = 50)
    String firstName;

    @NotEmpty
    @Column(name = "lastname", nullable = false, length = 50)
    String lastName;

    @NotEmpty
    @Column(name = "age", nullable = false, length = 20)
    int age;

    @Email
    @Column(name = "email", nullable = false, length = 50)
    String email;

    @NotEmpty
    @Column(name = "password", nullable = false, length = 30)
    String password;

    @Transient
    String userPasswordRepeat;

    @Transient
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
        return !isEmptyString(firstName) &&
                !isEmptyString(lastName) &&
                age > 0 && age < 150 &&
                !isEmptyString(email) &&
                !isEmptyString(password) &&
                password.equals(userPasswordRepeat) &&
                shoppingCard.isValidShoppingCard();
    }
}
