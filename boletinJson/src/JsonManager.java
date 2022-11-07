import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class JsonManager {
    public static JsonValue readJson(String path) {
        if (path.startsWith("http://")) {
            return readJsonHtpp(path);
        } else {
            return readJsonPath(path);
        }
    }

    private static JsonValue readJsonPath(String path) {
        try (JsonReader jsonReader = Json.createReader(new FileReader(path))) {
            return jsonReader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonValue readJsonHtpp(String path) {
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

    private static void readLocationWheatherData(String location) {
        System.out.println((getLocationWheatherData(location)));
    }

    private static JsonObject getLocationWheatherData(String location) {
        return readJson(String.format(
                "http://api.openweathermap.org/data/2.5/weather?q=%s,es&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0",
                location)).asJsonObject();
    }

    private static JsonObject getLocationWheatherData(double latitude, double longitude, int nearCities) {
        return readJson(String.format(
                "http://api.openweathermap.org/data/2.5/find?lat=%f&lon=%f&cnt=%d&APPID=8f8dccaf02657071004202f05c1fdce0",
                latitude, longitude, nearCities)).asJsonObject();
    }

    public static WeatherCityData getWeatherCityData(String location) {
        if (!locationExist(location)) {
            return null;
        }
        return new WeatherCityData(getLocationWheatherData(location));
    }

    public static WeatherCityData[] getWeatherCitysDatas(String location, int nearCities) {
        if (!locationExist(location)) {
            return null;
        }
        WeatherCityData mainLocation = getWeatherCityData(location);
        JsonArray cities = getLocationWheatherData(mainLocation.getLatitude(), mainLocation.getLongitude(), nearCities + 1).getJsonArray("list");
        WeatherCityData[] weatherCityDatas = new WeatherCityData[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            weatherCityDatas[i] = new WeatherCityData(cities.getJsonObject(i));
        }
        return weatherCityDatas;
    }

    public static boolean locationExist(String location) {
        JsonArray citys = readJson("res\\city.list.json").asJsonArray();
        for (int i = 0; i < citys.size(); i++) {
            if (citys.getJsonObject(i).getString("name").equalsIgnoreCase(location)) {
                return true;
            }
        }
        return false;
    }
}
