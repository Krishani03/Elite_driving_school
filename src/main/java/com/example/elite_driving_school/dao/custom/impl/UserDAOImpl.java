package com.example.elite_driving_school.dao.custom.impl;

import com.example.elite_driving_school.config.FactoryConfiguration;
import com.example.elite_driving_school.dao.custom.UserDAO;
import com.example.elite_driving_school.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

        @Override
        public boolean save(User user, Session session) {
            session.persist(user);
            return true;
        }

        @Override
        public boolean update(User user, Session session) {
            session.merge(user);
            return true;
        }

        @Override
        public boolean delete(String id, Session session) {
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                return true;
            }
            return false;
        }

        @Override
        public User search(String id, Session session) {
            return session.get(User.class, id);
        }

        @Override
        public ArrayList<User> getAll(Session session) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return (ArrayList<User>) query.getResultList();
        }

        @Override
        public String getNextId(Session session) {
            Query<String> query = session.createQuery("SELECT id FROM User ORDER BY id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();
            if (lastId != null) {
                int lastNum = Integer.parseInt(lastId.substring(1));
                return String.format("U%04d", lastNum + 1);
            }
            return "U1001";
        }

    @Override
    public User getUserByUsername(String username, Session session) {
        Query<User> query = session.createQuery("FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
}

