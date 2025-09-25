package com.example.elite_driving_school.dao.custom;

import com.example.elite_driving_school.dao.CrudDAO;
import com.example.elite_driving_school.entity.User;
import org.hibernate.Session;

public interface UserDAO extends CrudDAO<User> {
    User getUserByUsername(String username, Session session);

}
