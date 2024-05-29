package com.example.utilityservice;

import javafx.event.ActionEvent;
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


public class UtilityServiceController {


    //Субсидированный тариф на ЖКУ
    double SUBSIDIZED_TARIFF;
    double FACT_TARIFF;

    @FXML
    private Button btnCalcSumDifference;

    @FXML
    private Button setting;

    @FXML
    private Button btnCalcUS;

    @FXML
    private Button btnRent;

    @FXML
    private Label factTariff;

    @FXML
    private TextField fieldHeating;

    @FXML
    private TextField fieldHotWater;

    @FXML
    private Label header;

    @FXML
    private Label subsidizedTariff;

    @FXML
    private Label sumDifference;

    @FXML
    private TextField sumFactHeating;

    @FXML
    private TextField sumFactHotWater;

    @FXML
    private Label sumHeating;

    @FXML
    private Label sumHotWater;

    @FXML
    private Label sumTotal;

    private MyFile myFile = new MyFile();

    String pathResource = "D:/utilityServiceData/";


    public void setSUBSIDIZED_TARIFF(double SUBSIDIZED_TARIFF) throws IOException, ParseException {
        this.SUBSIDIZED_TARIFF = myFile.getDataFromJsonFile("SubTariff");
    }

    public void setFACT_TARIFF(double FACT_TARIFF) throws IOException, ParseException {
        this.FACT_TARIFF = myFile.getDataFromJsonFile("factTariff");
    }

    @FXML
    void initialize() throws IOException, ParseException {
        //Задаем значения тарифов
        SUBSIDIZED_TARIFF = myFile.getDataFromJsonFile("subTariff");
        FACT_TARIFF = myFile.getDataFromJsonFile("factTariff");

        //Присваем Label значения
        subsidizedTariff.setText(String.valueOf(SUBSIDIZED_TARIFF));
        factTariff.setText(String.valueOf(FACT_TARIFF));

        sumHotWater.setText("null BYN");
        sumHeating.setText("null BYN");
        sumDifference.setText("null BYN");
        sumTotal.setText("null BYN");

    }

    @FXML
    void calcUS(){
        try {
            sumHotWater.setText(parseNumber(calculateSum("Hot Water")) + " " + "BYN");
            sumHeating.setText(parseNumber(calculateSum("Heating")) + " " + "BYN");
            sumTotal.setText(parseNumber(calculateSum("Total Sum")) + " " + "BYN");
            sumDifference.setText(parseNumber(calculateSum("Sum Difference")) + " " + "BYN");

            myFile.setDataToJsonFile(calculateSum("Sum Difference"),"sumDifference");

            /*MyFile myFile = new MyFile();
            myFile.addInfoToFile(calculateSum("Sum Difference"));*/

        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Не все поля заполнены или данные в них некорректны");
            alert.showAndWait();
        }

    }

    //Округляем число до двух цифр после запятой
    private String parseNumber(Double number){
        return String.valueOf(String.format("%.2f",number));
    }

    //Все формулы подчетов
    private Double calculateSum(String choice){

        fieldHotWater.setText(fieldHotWater.getText().replaceAll(",","."));
        fieldHeating.setText(fieldHeating.getText().replaceAll(",","."));
        sumFactHotWater.setText(sumFactHotWater.getText().replaceAll(",","."));
        sumFactHeating.setText(sumFactHeating.getText().replaceAll(",","."));

        double sum = 0;

        switch (choice){
            case "Hot Water" :
                sum = SUBSIDIZED_TARIFF * (Double.parseDouble(fieldHotWater.getText()));
                break;

            case "Heating" :
                sum = SUBSIDIZED_TARIFF * (Double.parseDouble(fieldHeating.getText()));
                break;

            case "Total Sum" :
                sum = SUBSIDIZED_TARIFF * (Double.parseDouble(fieldHotWater.getText()) + (Double.parseDouble(fieldHeating.getText())));
                break;

            case "Sum Difference" :
                double sumFactPaid = Double.parseDouble(sumFactHotWater.getText()) + Double.parseDouble(sumFactHeating.getText());
                sum = sumFactPaid - (SUBSIDIZED_TARIFF * (Double.parseDouble(fieldHotWater.getText()) + (Double.parseDouble(fieldHeating.getText())))) ;
                break;
        }

        return sum;
        //Округляем число до 2 знаков после запятой
        //return String.valueOf(String.format("%.2f", sum));
    }


    //Открываем вкладку с оплатой аренды
    @FXML
    public void clickBtnRent(ActionEvent event) {

        Stage currentStage = (Stage) btnRent.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/utilityservice/tabRent.fxml"));
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

    //Открываем вкладку с настройками
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
