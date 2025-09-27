package com.example.elite_driving_school.bo.custom.impl;

import com.example.elite_driving_school.bo.custom.UserBO;
import com.example.elite_driving_school.dao.DAOFactory;
import com.example.elite_driving_school.dao.custom.UserDAO;
import com.example.elite_driving_school.dto.UserDTO;
import com.example.elite_driving_school.entity.User;
import com.example.elite_driving_school.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public UserDTO saveUser(UserDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            String nextId = userDAO.getNextId(session);

            User.Role userRole = User.Role.valueOf(dto.getRole().toUpperCase());
            User user = new User(
                    nextId,
                    dto.getUsername(),
                    hashPassword(dto.getPassword()),
                    userRole,
                    dto.isActive()
            );

            userDAO.save(user, session);
            transaction.commit();

            dto.setId(nextId); // return DTO with generated ID
            return dto;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateUser(UserDTO dto) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            User.Role userRole = User.Role.valueOf(dto.getRole().toUpperCase());

            User user = new User(
                    dto.getId(),
                    dto.getUsername(),
                    hashPassword(dto.getPassword()),
                    userRole,
                    dto.isActive()
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
                return new UserDTO(user.getId(), user.getUsername(),
                        user.getPasswordHash(), user.getRole().name(), user.isActive());
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<User> users = userDAO.getAll(session);
            ArrayList<UserDTO> dtoList = new ArrayList<>();
            for (User user : users) {
                dtoList.add(new UserDTO(user.getId(), user.getUsername(),
                        user.getPasswordHash(), user.getRole().name(), user.isActive()));
            }
            return dtoList;
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
                return new UserDTO(user.getId(), user.getUsername(),
                        user.getPasswordHash(), user.getRole().name(), user.isActive());
            }
            return null;
        } finally {
            session.close();
        }
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
