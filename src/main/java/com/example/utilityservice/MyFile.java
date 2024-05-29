package com.example.utilityservice;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class MyFile {

    //Файл в Target

    private String  subTariff;

    private String factTariff;

    private String cost;

    //private String path = MyFile.class.getResource("/com/example/utilityservice/file.txt").getPath();

    //private String path = "D:/utilityServiceData/file1.json";
//    public void addInfoToFile(String totalSumDifference) throws IOException {
//        //Записываем в файл значения разницы в комунальных платежах(по горячей и холодной воде)
//        FileWriter fileWriter = new FileWriter(path);
//        fileWriter.write(totalSumDifference);
//        fileWriter.close();
 //   }

//    public String getInfoFromFile() {
//        String str;
//        try {
//
//            //Читаем файл
//            FileReader fileReader = new FileReader(path);
//            BufferedReader reader = new BufferedReader(fileReader);
//            str = reader.readLine();
//
//            //Закрываем потоки
//            fileReader.close();
//            reader.close();
//
//            //В конце сессии опустошаем файл
//            FileWriter fileWriter = new FileWriter(path);
//            fileWriter.write("");
//            fileWriter.close();
//        }
//        catch (Exception e){
//            return "Введите сначала данные ЖКУ";
//        }
//            return str;
//    }

  /*  public void setData(String data, String type) throws IOException, ParseException {
        data.replaceAll(",",".");
        UtilityServiceController utilityServiceController = new UtilityServiceController();
        RentController  rentController = new RentController();
        switch (type){
            case "subTariff":
                subTariff = data;
                utilityServiceController.setSUBSIDIZED_TARIFF(Double.parseDouble(data));
                break;
            case "factTariff" :
                factTariff = data;
                utilityServiceController.setFACT_TARIFF(Double.parseDouble(data));
                break;
            case "cost" :
                rentController.setPRICE_OF_FLAT(Double.parseDouble(data));
                cost = data;
                break;
        }
    }*/

    public void setDataToJsonFile(Double data, String type) throws IOException, ParseException {


        String path = MyFile.class.getResource("/com/example/utilityservice/file1.json").getPath();
        Object o = new JSONParser().parse(new FileReader(path));

        JSONObject j = (JSONObject) o;

        switch (type) {

            case "subTariff":
                j.put("SubTariff", data);
                break;

            case "factTariff":
                j.put("FactTariff", data);
                break;

            case "costOfFlat":
                j.put("PriceOfFlat", data);
                break;

            case "sumDifference":
                Double localData = Double.parseDouble((
                        String.valueOf(
                                String.format("%.2f",data))).replaceAll(",","."));
                j.put("SumDifference", localData);
                break;
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(j.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Double getDataFromJsonFile(String type) throws IOException, ParseException {

        String path = MyFile.class.getResource("/com/example/utilityservice/file1.json").getPath();
        Object o = new JSONParser().parse(new FileReader(path));

        JSONObject j = (JSONObject) o;

        switch (type){
            case "subTariff":
                Double subTariff = (Double) j.get("SubTariff");
                return subTariff;
            case "factTariff" :
                Double factTariff = (Double) j.get("FactTariff");
                return factTariff;
            case "costOfFlat" :
                Double priceOfFlat = (Double) j.get("PriceOfFlat");
                return priceOfFlat;
            case "sumDifference" :
                Double sumDifference = (Double) j.get("SumDifference");
                return sumDifference;

        }

        return 0.0;
    }


}
