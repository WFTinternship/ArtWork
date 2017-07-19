package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.AbstractUserRepo;
import am.aca.wftartproject.repository.ShoppingCardRepo;
import am.aca.wftartproject.repository.UserRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.util.HashGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

/**
 * Created by surik on 6/3/17
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    AbstractUserRepo abstractUserRepo ;


    /**
     * @param user
     * @see UserService#addUser(User)
     */
    @Override
    public void addUser(User user) {
        if (user == null || !user.isValidUser() || !isValidEmailAddressForm(user.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, user));
            throw new InvalidEntryException(error);
        }

        try{
            if (abstractUserRepo.findByEmail(user.getEmail()) != null) {
                String error = "User has already exists";
                LOGGER.error(String.format("Failed to add User: %s: %s", error, user));
                throw new DuplicateEntryException(error);
            }
        }catch (DAOException e){
        }

        try {
            //encrypt user password
            String encryptedPassword = HashGenerator.generateHashString(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepo.saveAndFlush(user);
        } catch (DAOException e) {
            String error = "Failed to add User: %s";
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
            return userRepo.findOne(id);
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
            return userRepo.findUserByEmail(email);
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param user
     * @see UserService#updateUser( User)
     */
    @Override
    public void updateUser(User user) {

        if (user == null || !user.isValidUser() || user.getId() == null || user.getId() < 0) {
            LOGGER.error(String.format("User is not valid: %s", user));
            throw new InvalidEntryException("Invalid user");
        }

        try {
            //encrypt user password
            String encryptedPassword = HashGenerator.generateHashString(user.getPassword());
            user.setPassword(encryptedPassword);
            if (userRepo.saveAndFlush(user) == null) {
                throw new DAOException("Failed to update user");
            }
        } catch (DAOException e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param user
     * @see UserService#deleteUser(User)
     */
    @Override
    public void deleteUser(User user) {
        if (user == null || !user.isValidUser()) {
            LOGGER.error(String.format("User is not valid: %s", user));
            throw new InvalidEntryException("Invalid User");
        }

        try {
             userRepo.delete(user);
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
        User user1 = null;
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            User user = userRepo.findUserByEmail(email);
            String hashedPassword = HashGenerator.generateHashString(password);
            if (user != null && user.getPassword().equals(hashedPassword)) {
               user1 = user;
            }
            else throw new DAOException("");
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        } catch (RuntimeException e) {
            String error = "Failed to login: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
        return user1;
    }
}
