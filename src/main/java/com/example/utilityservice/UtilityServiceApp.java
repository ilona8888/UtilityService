package com.example.utilityservice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UtilityServiceApp extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(UtilityServiceApp.class.getResource("sample.fxml"));
        //Задаем размеры окна
        Scene scene = new Scene(fxmlLoader.load(), 684, 488);
        //Задаем заголовок
        stage.setTitle("Аренда ул. Сухаревская 1, кв. 5");
        //Ограничение: нельзя изменять размер окна
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        MyFile myFile = new MyFile();
        myFile.setDataToJsonFile(0.00,"sumDifference");
    }

    public static void main(String[] args) {
        launch();
    }

}
