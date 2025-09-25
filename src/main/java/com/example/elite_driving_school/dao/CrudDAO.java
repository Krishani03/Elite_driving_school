package com.example.elite_driving_school.dao;

import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T t, Session session) throws SQLException;

    boolean update(T t, Session session) throws SQLException;

    boolean delete(String id, Session session) throws SQLException;

    T search(String id, Session session) throws SQLException;

    List<T> getAll(Session session) throws SQLException;

    String getNextId(Session session) throws SQLException;

}
