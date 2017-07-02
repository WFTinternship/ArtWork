package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

/**
 * Created by surik on 6/3/17
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    private ShoppingCardDao shoppingCardDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setShoppingCardDao(ShoppingCardDao shoppingCardDao) {
        this.shoppingCardDao = shoppingCardDao;
    }

    /**
     * @param user
     * @see UserService#addUser(User)
     */
    @Override
    @Transactional
    public void addUser(User user) {
        if (user == null || !user.isValidUser() || !isValidEmailAddressForm(user.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, user));
            throw new InvalidEntryException(error);
        }

        if (userDao.findUser(user.getEmail()) != null) {
            String error = "User has already exists";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, user));
            throw new DuplicateEntryException(error);
        }

        try {
            userDao.addUser(user);
        } catch (DAOException e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

        try {
            shoppingCardDao.addShoppingCard(user.getId(),user.getShoppingCard());
        } catch (DAOException e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @return
     * @see UserService#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            return userDao.findUser(id);
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param email
     * @return
     * @see UserService#findUser(String)
     */
    @Override
    public User findUser(String email) {
        if (isEmptyString(email) || !isValidEmailAddressForm(email)) {
            LOGGER.error(String.format("Email is not valid: %s", email));
            throw new InvalidEntryException("Invalid email");
        }

        try {
            return userDao.findUser(email);
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @param user
     * @see UserService#updateUser(Long, User)
     */
    @Override
    @Transactional
    public void updateUser(Long id, User user) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }
        if (user == null || !user.isValidUser()) {
            LOGGER.error(String.format("User is not valid: %s", user));
            throw new InvalidEntryException("Invalid user");
        }

        try {
            if (!userDao.updateUser(id, user)) {
                throw new DAOException("Failed to update user");
            }
        } catch (DAOException e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @see UserService#deleteUser(Long)
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            if (!userDao.deleteUser(id)) {
                throw new DAOException("Failed to delete user");
            }
        } catch (DAOException e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param email
     * @param password
     * @return
     * @see UserService#login(String, String)
     */
    @Override
    public User login(String email, String password) {
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            User user = userDao.findUser(email);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                throw new RuntimeException("The username or password is not correct");
            }
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        } catch (RuntimeException e) {
            String error = "Failed to login: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
