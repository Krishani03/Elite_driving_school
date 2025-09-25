package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.UserBO;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.UserDAO;
import com.example.elite_driving_school.dto.UserDTO;
import com.example.elite_driving_school.entity.User;
import com.example.elite_driving_school.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

//    @Override
//    public boolean saveUser(UserDTO dto) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            User user = new User(dto.getUsername(), hashPassword(dto.getPassword()), dto.getRole(), dto.isActive());
//            boolean result = userDAO.save(user, session);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            session.close();
//        }
//    }
@Override
public boolean saveUser(UserDTO dto) {
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();
    try {
        User.Role userRole = User.Role.valueOf(dto.getRole().toUpperCase());

        User user = new User(
                dto.getUsername(),
                hashPassword(dto.getPassword()),
                userRole,
                dto.isActive()
        );

        boolean result = userDAO.save(user, session);
        transaction.commit();
        return result;
    } catch (Exception e) {
        if (transaction != null) transaction.rollback();
        e.printStackTrace();
        return false;
    } finally {
        session.close();
    }
}



    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

//    @Override
//    public boolean updateUser(UserDTO dto) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            User user = new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getRole());
//            boolean result = userDAO.update(user, session);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            session.close();
//        }
//    }
@Override
public boolean updateUser(UserDTO dto) {
    Session session = FactoryConfiguration.getInstance().getSession();
    Transaction transaction = session.beginTransaction();
    try {
        // Convert DTO role to Entity Role enum
        // dto.getRole() returns a String, e.g., "admin" or "receptionist"
        User.Role userRole = User.Role.valueOf(dto.getRole().toUpperCase());

        User user = new User(
                dto.getId(),
                dto.getUsername(),
                hashPassword(dto.getPassword()), // rehash password if updating
                userRole
        );

        boolean result = userDAO.update(user, session);
        transaction.commit();
        return result;
    } catch (Exception e) {
        if (transaction != null) transaction.rollback();
        e.printStackTrace();
        return false;
    } finally {
        session.close();
    }
}


    @Override
    public boolean deleteUser(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean result = userDAO.delete(id, session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public UserDTO searchUser(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            User user = userDAO.search(id, session);
            if (user != null) {
                return new UserDTO(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole());
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            ArrayList<User> users = userDAO.getAll(session);
            ArrayList<UserDTO> dtoList = new ArrayList<>();
            for (User user : users) {
                dtoList.add(new UserDTO(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole()));
            }
            return dtoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public String getNextUserId() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return userDAO.getNextId(session);
        } finally {
            session.close();
        }
    }

    @Override
    public UserDTO getUserByUsername(String username) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            User user = userDAO.getUserByUsername(username, session);
            if (user != null) {
                return new UserDTO(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole().name(), user.isActive());
            }
            return null;
        } finally {
            session.close();
        }
    }

}
