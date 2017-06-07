package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.validator.ValidatorUtil;
import am.aca.wftartproject.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.*;

/**
 * Created by surik on 6/3/17
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
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
        if (user == null || !user.isValidUser()){
            LOGGER.error(String.format("user is invalid: %s", user));
            throw new ServiceException("Invalid user");
        }
        try {
            userDao.addUser(user);
        }catch (DAOException e){
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     * @return
     *
     * @see UserService#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid Id");
        }
        try {
            return userDao.findUser(id);
        }catch (DAOException e){
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param email
     * @return
     *
     * @see UserService#findUser(String)
     */
    @Override
    public User findUser(String email) {
        if (!isEmptyString(email)){
            LOGGER.error(String.format("email is invalid: %s", email));
            throw new ServiceException("Invalid email");
        }
        try {
            return userDao.findUser(email);
        }catch (DAOException e){
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     * @param user
     *
     * @see UserService#updateUser(Long, User)
     */
    @Override
    public void updateUser(Long id, User user) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid Id");
        }
        if (user == null || !user.isValidUser()){
            LOGGER.error(String.format("user is invalid: %s", user));
            throw new ServiceException("Invalid user");
        }
        try{
            userDao.updateUser(id, user);
        }catch (DAOException e){
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     *
     * @see UserService#deleteUser(Long)
     */
    @Override
    public void deleteUser(Long id) {

        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid Id");
        }
        try {
            userDao.deleteUser(id);
        }catch (DAOException e){
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
