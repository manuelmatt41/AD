package boletin3;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonValue;

public class JsonManager {
    public JsonValue readJson(String path) {
        if (path.startsWith("http://")) {
            return readJsonHtpps(path);
        }
        return null;
    }

    public JsonValue readJsonHtpps(String path) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream is = connection.getInputStream()) {
            return Json.createReader(is).readValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void readLocationWheatherData(String location) {
        System.out.println(readJson(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,es&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0", location)));
    }
}
