import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class JsonManager {
    public JsonValue readJson(String path) {
        if (path.startsWith("http://")) {
            return  readJsonHtpp(path);
        }
        return null;
    }

    public JsonValue readJsonHtpp(String path) {
        URL url = null;
        HttpURLConnection connectionHttp = null;
        try {
            url = new URL(path);
            connectionHttp = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream is = connectionHttp.getInputStream()) {
            return Json.createReader(is).readValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void readLocationWheatherData(String location) {
        System.out.println((getLocationWheatherData(location)));
    }

    public JsonObject getLocationWheatherData(String location) {
        return readJson(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,es&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0", location)).asJsonObject();
    }

    public void readLocationWheatherData(double longitude, double latitude) {
        System.out.println(readJson(String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&APPID=8f8dccaf02657071004202f05c1fdce0", latitude, longitude)).asJsonObject());
    }

    public void readLocationWheatherData(double longitude, double latitude, int countPredictions) {
        System.out.println(readJson(String.format("http://api.openweathermap.org/data/2.5/find?lat=%f&lon=%f&cnt=%d&APPID=8f8dccaf02657071004202f05c1fdce0", latitude, longitude, countPredictions)).asJsonObject());
    }
    public JsonObject getLocationWheatherData(double longitude, double latitude) {
        return readJson(String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&APPID=8f8dccaf02657071004202f05c1fdce0", latitude, longitude)).asJsonObject();
    }

    public int getLocationIdFromLocation(String location) {
        return getLocationWheatherData(location).getInt("id");
    }

    public String getLocationNameFromCoordinates(double longitude, double latitude) {
        return getLocationWheatherData(longitude, latitude).getString("name");
    }

    public String getCoordinatesFromLocation(String location) {
        return String.format("Longitude: %f || Latitude: %f",getLocationWheatherData(location).getJsonObject("coord").getJsonNumber("lon").doubleValue(),getLocationWheatherData(location).getJsonObject("coord").getJsonNumber("lat").doubleValue());
    }

    public Object getWheatherDataInt(String dataName, String location) {
        return getLocationWheatherData(location);
    }
    public Object getWheatherDataString(String dataName, String location) {
        return getLocationWheatherData(location);
    }
    public Object getWheatherDataDouble(String dataName, String location) {
        return getLocationWheatherData(location);
    }
    public Object getWheatherDataBoolean(String dataName, String location) {
        return getLocationWheatherData(location);
    }
    public String getWheatherDataDate(String dataName, String location) {
        return Instant.ofEpochSecond(getLocationWheatherData(location).getJsonNumber(dataName).longValue()).atZone(ZoneId.of("GMT+1")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
