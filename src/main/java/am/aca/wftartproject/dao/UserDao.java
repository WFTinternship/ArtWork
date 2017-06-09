package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.User;

/**
 * Created by ASUS on 27-May-17
 */
public interface UserDao {

    /**
     * Adds user to the springconfig.database
     *
     * @param user
     */
    void addUser(User user);

    /**
     * Finds user with the following id.
     *
     * @param id
     * @return
     */
    User findUser(Long id);

    /**
     * Finds user with the following email.
     *
     * @param email
     * @return
     */
    User findUser(String email);

    /**
     * Updates user info
     *
     * @param id
     * @param user
     */
    Boolean updateUser(Long id, User user);

    /**
     * Deletes user with the following id.
     *
     * @param id
     */
    Boolean deleteUser(Long id);


}
