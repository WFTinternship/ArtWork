package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.AbstractUserRepo;
import am.aca.wftartproject.repository.UserRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.util.HashGenerator;
import am.aca.wftartproject.util.ServiceHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;


@Service
public class UserServiceImpl extends ServiceHelper implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private UserRepo userRepo;

    private AbstractUserRepo abstractUserRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Autowired
    public void setAbstractUserRepo(AbstractUserRepo abstractUserRepo) {
        this.abstractUserRepo = abstractUserRepo;
    }
    /**
     * @param user*
     * @see UserService#addUser(User)
     */
    @Override
    public void addUser(User user) {

        //check user for validity
        if (user == null || !user.isValidUser() || !isValidEmailAddressForm(user.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, user));
            throw new InvalidEntryException(error);
        }

        //find user from db
        findAbsUser(abstractUserRepo,user);

        //encrypt user password set and save user into db
        try {
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
     * @param id*
     * @see UserService#findUser(Long)
     */
    @Override
    public User findUser(Long id) {

        //check user id for validity
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }

        //try to find user by id
        try {
            return userRepo.findOne(id);
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param email*
     * @see UserService#findUser(String)
     */
    @Override
    public User findUser(String email) {


        //check email for validity
        if (isEmptyString(email) || !isValidEmailAddressForm(email)) {
            LOGGER.error(String.format("Email is not valid: %s", email));
            throw new InvalidEntryException("Invalid email");
        }

        //find user from db by email
        try {
            return userRepo.findUserByEmail(email);
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param user*
     * @see UserService#updateUser(User)
     */
    @Override
    public void updateUser(User user) {

        //check user for validity
        if (user == null || !user.isValidUser() || user.getId() == null || user.getId() < 0) {
            LOGGER.error(String.format("User is not valid: %s", user));
            throw new InvalidEntryException("Invalid user");
        }

        //encrypt user password set and update it in db
        try {
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
     * @param user*
     * @see UserService#deleteUser(User)
     */
    @Override
    public void deleteUser(User user) {

        //check user for validity
        if (user == null || !user.isValidUser()) {
            LOGGER.error(String.format("User is not valid: %s", user));
            throw new InvalidEntryException("Invalid User");
        }

        //try to delete user from db
        try {
            userRepo.delete(user);
        } catch (DAOException e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param email*
     * @param password*
     * @see UserService#login(String, String)
     */
    @Override
    public User login(String email, String password) {
        User user1;

        //check users email and password for validity
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }

        // find user from db by email
        try {
            User user = userRepo.findUserByEmail(email);
            String hashedPassword = HashGenerator.generateHashString(password);
            if (user != null && user.getPassword().equals(hashedPassword)) {
                user1 = user;
            } else throw new DAOException("Failed to find User");
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } catch (RuntimeException e) {
            String error = "Failed to login: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
        return user1;
    }
}
