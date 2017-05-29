package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17.
 */
public class UserDaoImpl implements UserDao {

    Connection conn = null;

    public UserDaoImpl() throws SQLException, ClassNotFoundException {
        conn = new DBConnection().getDBConnection();
    }

//    private static volatile UserDaoImpl userDaoImplInstance;
//    private UserDaoImpl() throws SQLException, ClassNotFoundException {
//        conn = new DBConnection().getDBConnection();
//    }
//    public static UserDaoImpl getUserDaoImplInstance() throws SQLException, ClassNotFoundException {
//        if(userDaoImplInstance == null){
//            synchronized (UserDaoImpl.class) {
//                if(userDaoImplInstance == null) {
//                    userDaoImplInstance = new UserDaoImpl();
//                }
//            }
//        }
//        return userDaoImplInstance;
//    }


    @Override
    public void addUser(User user) {
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)")) {

            ps.setString(1,user.getFirstName());
            ps.setString(2,user.getLastName());
            ps.setInt(3,user.getAge());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getPassword());

            if(ps.executeUpdate()>0){
                System.out.println("The user was successfully inserted");
            }else{
                System.out.println("There is problem with user insertion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateUser(int id, User user) {

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE TABLE user SET firstname=? WHERE id = ?");
            ps.setString(1,user.getFirstName());
            ps.setInt(2,id);
            if(ps.executeUpdate()>0){
                System.out.println("The user info was successfully updated");
            }else{
                System.out.println("There is problem with user info updating");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id =?");
            ps.setInt(1,id);
            if(ps.executeUpdate()>0){
                System.out.println("The user was successfully deleted");
            }else{
                System.out.println("There is problem with user deletion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findUser(int id) {
        User user = new User();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUser(String email){
        User user = new User();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

}
