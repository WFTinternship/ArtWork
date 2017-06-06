package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by surik on 6/3/17
 */
public class UserServiceImpl implements UserService {

    private Connection dbConnection = new DBConnection().getDBConnection(DBConnection.DBType.REAL);
    private UserDao userDao = new UserDaoImpl(dbConnection);

    public UserServiceImpl() throws SQLException, ClassNotFoundException {
    }

    /**
     * @param user
     *
     * @see UserService#addUser(User)
     */
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    /**
     * @param id
     * @return
     *
     * @see UserService#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }

    /**
     * @param email
     * @return
     *
     * @see UserService#findUser(String)
     */
    @Override
    public User findUser(String email) {
        return userDao.findUser(email);
    }

    /**
     * @param id
     * @param user
     *
     * @see UserService#updateUser(Long, User)
     */
    @Override
    public void updateUser(Long id, User user) {
        userDao.updateUser(id, user);
    }

    /**
     * @param id
     *
     * @see UserService#deleteUser(Long)
     */
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}
