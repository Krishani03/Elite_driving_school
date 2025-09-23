package com.example.elite_driving_school.dao;

import com.example.elite_driving_school.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory() {}
    public static DAOFactory getInstance() {
        return (daoFactory==null)?new DAOFactory():daoFactory;
    }
    public enum DAOType {
        USER,
        STUDENT,
        PAYMENT,
        LESSON,
        INSTRUCTOR,
        COURSE,
    }
    public SuperDAO getDAO(DAOType daoType) {
        switch(daoType){
            case USER:
                return new UserDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case LESSON:
                return new LessonDAOImpl();
            case INSTRUCTOR:
                return new InstructorDAOImpl();
            case COURSE:
                return new CourseDAOImpl();
            default:
                return null;
        }
    }
}
