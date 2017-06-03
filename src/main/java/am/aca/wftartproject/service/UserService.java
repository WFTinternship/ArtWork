package am.aca.wftartproject.service;

import am.aca.wftartproject.model.User;

/**
 * Created by ASUS on 30-May-17
 */
public interface UserService {

    /**
     * Add User to the database
     *
     * @param user
     */
    void addUser(User user);

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
}
