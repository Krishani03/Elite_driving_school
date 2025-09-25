package com.example.elite_driving_school;

import com.example.elite_driving_school.bo.custom.UserBO;
import com.example.elite_driving_school.bo.custom.impl.UserBOImpl;
import com.example.elite_driving_school.dto.UserDTO;

public class DataInitializer {

        private static final UserBO userBO = new UserBOImpl();

        public static void init() {
            try {
                // Create Admin user if not exists
                if (userBO.getUserByUsername("admin") == null) {
                    UserDTO admin = new UserDTO();
                    admin.setUsername("admin");
                    admin.setPassword("admin123");
                    admin.setRole("ADMIN");
                    admin.setActive(true);
                    userBO.saveUser(admin);
                    System.out.println("Default admin created!");
                }

                // Create Receptionist user if not exists
                if (userBO.getUserByUsername("receptionist") == null) {
                    UserDTO receptionist = new UserDTO();
                    receptionist.setUsername("receptionist");
                    receptionist.setPassword("recep123");
                    receptionist.setRole("RECEPTIONIST");
                    receptionist.setActive(true);
                    userBO.saveUser(receptionist);
                    System.out.println("Default receptionist created!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


