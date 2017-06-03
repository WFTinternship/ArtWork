package am.aca.wftartproject.service;

import am.aca.wftartproject.util.DBConnection;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ASUS on 30-May-17
 */
public class UserServiceImpl implements UserService {

    private Connection dbConnection = new DBConnection().getDBConnection(DBConnection.DBType.REAL);
    private UserDao userDao = new UserDaoImpl(dbConnection);

    public UserServiceImpl() throws SQLException, ClassNotFoundException {
    }


    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        userDao.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
            userDao.deleteUser(id);
    }

    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }

    @Override
    public User findUser(String email) {
        return userDao.findUser(email);
    }
}
