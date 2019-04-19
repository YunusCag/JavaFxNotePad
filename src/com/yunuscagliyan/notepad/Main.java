package com.yunuscagliyan.notepad;

import com.yunuscagliyan.notepad.datamodel.NotData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void init() throws Exception {
        try {
            NotData.getInstance().readNotesOnFile();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("NotePad");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        try {
            NotData.getInstance().writeNotesOnFile();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
