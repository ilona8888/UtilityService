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

public class SettingController {

    @FXML
    private Button btnCalcUS;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnRent;

    @FXML
    private Label cost;

    @FXML
    private Label factTariff;

    @FXML
    private TextField fieldCost;

    @FXML
    private TextField fieldFactTariff;

    @FXML
    private TextField fieldSubTariff;

    @FXML
    private Label header;

    @FXML
    private Button setting;

    @FXML
    private Label subTariff;

    MyFile myFile = new MyFile();


    private Double createCorrectData(String data){
        data.replaceAll(",",".");
        return Double.parseDouble(data);
    }


    @FXML
    void change(ActionEvent event) throws IOException, ParseException {
        // Проверяем, хотя бы одно поле не пустое
        boolean anyFieldNotEmpty = !fieldSubTariff.getText().isEmpty() || !fieldFactTariff.getText().isEmpty() || !fieldCost.getText().isEmpty();

        // Если хотя бы одно поле не пустое, проверяем остальные поля
        if (anyFieldNotEmpty) {
            boolean allFieldsValid = true;

            // Проверяем поля на корректность
            if (!fieldSubTariff.getText().isEmpty()) {
                try {
                    myFile.setDataToJsonFile(createCorrectData(fieldSubTariff.getText()), "subTariff");
                } catch (NumberFormatException e) {
                    allFieldsValid = false;
                    alertErrorShow();
                }
            }

            if (!fieldFactTariff.getText().isEmpty()) {
                try {
                    myFile.setDataToJsonFile(createCorrectData(fieldFactTariff.getText()), "factTariff");
                } catch (NumberFormatException e) {
                    allFieldsValid = false;
                    alertErrorShow();
                }
            }

            if (!fieldCost.getText().isEmpty()) {
                try {
                    myFile.setDataToJsonFile(createCorrectData(fieldCost.getText()), "costOfFlat");
                } catch (NumberFormatException e) {
                    allFieldsValid = false;
                    alertErrorShow();
                }
            }

            if (allFieldsValid) {
                alertSuccessShow();
            }
        } else {
            alertWarningShow(); // Если все поля пустые, показываем предупреждение
        }
    }

    private void alertSuccessShow(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Успешно обновлено");
        alert.setHeaderText(null);
        alert.setContentText("Успешно произведена замена");
        alert.showAndWait();
    }

    private void alertErrorShow(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Данные в полях некорректны");
        alert.showAndWait();
    }

    private void alertWarningShow(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Данные в полях пусты");
        alert.showAndWait();
    }

    //Открыть вкладку с ЖКУ
    @FXML
    void clickBtnCalcUS(ActionEvent event) {
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


    //Открыть вкладку с арендной платой
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

}
