package com.example.utilityservice;

//import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpUtil {

    /**
     * @param url     - required
     * @param headers - nullable
     * @param request - nullable
     */
    public static String sendRequest(String url, Map<String, String> headers, String request) {
        String result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(20000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET");

            if (request != null) {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                outputStream.writeBytes(request);
                outputStream.flush();
                outputStream.close();
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int status = urlConnection.getResponseCode();
            System.out.println("status code:" + status);

            if (status == HttpURLConnection.HTTP_OK) {
                result = getStringFromStream(urlConnection.getInputStream());
            }
        } catch (Exception e) {
            System.out.println("sendRequest failed");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private static String getStringFromStream(InputStream inputStream) throws IOException {
        final int BUFFER_SIZE = 4096;
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream(BUFFER_SIZE);
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            resultStream.write(buffer, 0, length);
        }
        return resultStream.toString("UTF-8");
    }
    
    public String getExchangeRate(String currency) throws MalformedURLException {
        //String url = "https://api.privatbank.ua/p24api/exchange_rates?json&date=01.12.2016";
        URL url = new URL("https://www.nbrb.by/api/exrates/rates/" + currency + "?parammode=2");
        String result = HttpUtil.sendRequest(url.toString(), null, null);
        System.out.println("Result: " + result);

        String regex = "\"Cur_OfficialRate\":\\s*([\\d.]+)";

        // Компиляция регулярного выражения
        Pattern pattern = Pattern.compile(regex);

        // Создание Matcher для строки input
        Matcher matcher = pattern.matcher(result);

        String rate = null;

        // Поиск соответствия
        if (matcher.find()) {
            // Получение найденного числа
            rate = matcher.group(1);
            System.out.println(rate);
        } else {
            System.out.println("Число после \"Cur_OfficialRate\" не найдено.");
        }

        return rate;
    }

}
