package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.User;

/**
 * Created by ASUS on 27-May-17.
 */
public interface UserDao {

    void addUser(User user);
    void updateUser(int id, User user);
    void deleteUser(int id);
    User findUser(int id);
    User findUser(String email);

}
