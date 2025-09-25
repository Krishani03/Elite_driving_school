package com.example.elite_driving_school;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {


        public static void main(String[] args) {
            com.example.elite_driving_school.DataInitializer.init(); // initialize default users
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(
                        getClass().getResource("/com/example/elite_driving_school/view/loginForm.fxml")
                ));

                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Elite Driving School");
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}


