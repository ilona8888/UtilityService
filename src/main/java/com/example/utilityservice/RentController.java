package com.example.utilityservice;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class RentController {

    @FXML
    private Label dollar;

    @FXML
    private Button setting;

    @FXML
    private Button btnCalcRent;

    @FXML
    private Button btnCalcUS;

    @FXML
    private Button btnRent;

    @FXML
    private TextField fieldDollar;

    @FXML
    private TextField fieldSumDifference;

    @FXML
    private Label header;

    @FXML
    private Label sumRent;

    @FXML
    private Label wePayDollar;

    @FXML
    private Label sumTotal;

    MyFile myFile = new MyFile();

    //Сумма аренды в долларах
    private  double PRICE_OF_FLAT;

    @FXML
    void initialize() throws IOException, ParseException {
        Double sumTotalNumeric = null;
        String dollarTariff = "";

        PRICE_OF_FLAT = myFile.getDataFromJsonFile("costOfFlat");
        sumRent.setText("null" + " " + "BYN" + " + " + "100$");
        wePayDollar.setText(String.valueOf(myFile.getDataFromJsonFile("costOfFlat")) + " $ " );

        try{
            //sumTotalNumeric = (new MyFile().getInfoFromFile()).replaceAll(",",".");
            //sumTotalNumeric = new MyFile().getDataFromJsonFile("sumDifference")

            sumTotalNumeric = myFile.getDataFromJsonFile("sumDifference");
            if(sumTotalNumeric.equals(0.0)  == false)
            {
            sumTotal.setText(sumTotalNumeric + " " + "BYN");
            fieldSumDifference.setText(String.valueOf(sumTotalNumeric));
            }
            else throw new Exception();
        }
        catch (Exception e){
            sumTotal.setText("Telegram");
        }

        try {
            dollarTariff = new HttpUtil().getExchangeRate("USD");
            dollar.setText(dollarTariff + " " + "BYN");
            fieldDollar.setText(dollarTariff);
        }
        catch (Exception e){
            dollar.setText("0.00" + " " + "USD");
        }

        if(sumTotalNumeric.equals(0.0) == false && dollarTariff.equals("") == false) {
            double sum = (Double.parseDouble(dollarTariff) * PRICE_OF_FLAT) - sumTotalNumeric;
            sumRent.setText(String.format("%.2f", sum) + " " + "BYN" + " + " + "100$");
        }

     /*   if(dollarTariff.matches("-?\\d+(\\.\\d+)?") &&  sumTotalNumeric.matches("-?\\d+(\\.\\d+)?"))
        {
            double sum = (Double.parseDouble(dollarTariff) * PRICE_OF_FLAT) - Double.parseDouble(sumTotalNumeric);

            sumRent.setText(String.format("%.2f", sum) + " " + "BYN");
        }*/
    }

    @FXML
    void calcRent(){
        try {
            //Автоматизируем автоисправление запятой на точку
            fieldDollar.setText(fieldDollar.getText().replaceAll(",","."));
            fieldSumDifference.setText(fieldSumDifference.getText().replaceAll(",","."));

            //Высчитываем итогую оплату Арендодателю
            double sum = (Double.parseDouble(fieldDollar.getText()) * PRICE_OF_FLAT) - Double.parseDouble(fieldSumDifference.getText());

            // Меняем значения в поле подсказка на введенное в поле ввода
            sumTotal.setText(fieldSumDifference.getText() + " " + "BYN");
            dollar.setText(fieldDollar.getText() + " " + "USD");


            sumRent.setText(String.format("%.2f", sum) +  " " + "BYN" + " + " + "100$");

        }
        catch (Exception e){
            //System.out.println(e);

            //Выводим всплываю окно об ошибке ввода
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Не все поля заполнены или данные в них некорректны");
            alert.show();
        }
    }



    //Открываем вкладку с ЖКУ
    @FXML
    void clickBtnCalcUS(){
        Stage currentStage = (Stage) btnCalcUS.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/utilityservice/sample.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Аренда ул. Сухаревская 1, кв. 5");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void clickSetting(){
        Stage currentStage = (Stage) setting.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/utilityservice/setting.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Аренда ул. Сухаревская 1, кв. 5");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}

