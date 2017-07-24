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

    public User(String firstName, String lastName, int age, String email, String password,String userPasswordRepeat, ShoppingCard shoppingCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.shoppingCard = shoppingCard;
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
                ", shoppingCard='" + shoppingCard + '\'' +
                '}';
    }


    //region <UserBuilder>

//    public User(UserInfoBuilder userinfo) {
//        this.firstName = userinfo.firstName;
//        this.lastName = userinfo.lastName;
//        this.age = userinfo.age;
//        this.email = userinfo.email;
//        this.password = userinfo.password;
//        this.shoppingCard = userinfo.shoppingCard;
//    }
//
//    public static class UserInfoBuilder{
//        Long id;
//        String firstName;
//        String lastName;
//        int age;
//        String email;
//        String password;
//        ShoppingCard shoppingCard;
//
//        public UserInfoBuilder setId(Long id){
//            this.id = id;
//            return this;
//        }
//        public UserInfoBuilder setFirstName(String firstName){
//            this.firstName = firstName;
//            return this;
//        }
//
//        public UserInfoBuilder setLastName(String lastName){
//            this.lastName = lastName;
//            return this;
//        }
//        public UserInfoBuilder setAge(int age){
//            this.age = age;
//            return this;
//        }
//        public UserInfoBuilder setEmail(String email){
//            this.email = email;
//            return this;
//        }
//        public UserInfoBuilder setPassword(String password){
//            this.password = password;
//            return this;
//        }
//        public UserInfoBuilder setShoppingCard(ShoppingCard shoppingCard){
//            this.shoppingCard = shoppingCard;
//            return this;
//        }
//
//        public User build(){
//            return new User(this);
//        }
//    }

    //endregion
}
