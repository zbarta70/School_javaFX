
package com.school;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class School extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
                        
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Iskola");
        stage.setWidth(800);
        stage.setHeight(680);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
