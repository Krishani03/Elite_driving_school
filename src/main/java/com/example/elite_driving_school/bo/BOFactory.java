package com.example.elite_driving_school.bo;

import com.example.elite_driving_school.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory == null) ? new BOFactory() : boFactory;
    }
    public enum BOTypes{
        USER,
        STUDENT,
        PAYMENT,
        INSTRUCTOR,
        LESSON,
        COURSE,
    }
    public SuperBO getBO(BOTypes boType) {
        switch(boType){
            case USER:
                return new UserBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case INSTRUCTOR:
                return new InstructorBOImpl();
            case LESSON:
                return new LessonBOImpl();
            case COURSE:
                return new CourseBOImpl();
            default:
                return null;
        }
    }
}
