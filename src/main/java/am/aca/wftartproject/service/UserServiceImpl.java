package am.aca.wftartproject.service;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.daoInterfaces.impl.UserDaoImpl;
import am.aca.wftartproject.model.User;
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

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }

    @Override
    public User findUser(String email) {
        return userDao.findUser(email);
    }

    @Override
    public void updateUser(Long id, User user) {
        userDao.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}
