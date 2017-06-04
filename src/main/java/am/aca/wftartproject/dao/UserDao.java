package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.User;

/**
 * Created by ASUS on 27-May-17
 */
public interface UserDao {

    /**
     * Add User to the database
     *
     * @param user
     */
    void addUser(User user);


    /**
     * Find User with the following id.
     *
     * @param id
     * @return
     */
    User findUser(Long id);


    /**
     * Find User with the following email.
     *
     * @param email
     * @return
     */
    User findUser(String email);


    /**
     * Update user info
     *
     * @param id
     * @param user
     */
    void updateUser(Long id, User user);


    /**
     * Delete user with the following id.
     *
     * @param id
     */
    void deleteUser(Long id);

}
