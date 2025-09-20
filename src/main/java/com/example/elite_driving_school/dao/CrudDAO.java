package com.example.elite_driving_school.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    boolean save (T t) throws SQLException;
    boolean update (T t) throws SQLException;
    boolean delete (String id) throws SQLException;
    ArrayList<T> search (String id) throws SQLException;
    ArrayList <T> getAll () throws SQLException;
    String getNextId() throws SQLException;
}
