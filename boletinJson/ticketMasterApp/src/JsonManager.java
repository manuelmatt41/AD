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
import javax.net.ssl.HttpsURLConnection;

@SuppressWarnings("all")
public class JsonManager {
    public static JsonValue readJson(String path) {
        if (path.startsWith("http://")) {
            return readJsonHtpp(path);
        } else if (path.startsWith("https://")) {
            return readJsonHtpps(path);
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

    private static JsonValue readJsonHtpps(String path) {
        URL url = null;
        HttpsURLConnection connectionHttp = null;
        try {
            url = new URL(path);
            connectionHttp = (HttpsURLConnection) url.openConnection();
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

    public static JsonArray getEvents(String eventType) {
        return readJson(String.format("https://app.ticketmaster.com/discovery/v2/events.json?countryCode=ES&classificationName=%s&apikey=AMXR5Rf8zlr7oGucsebGKvDCLOQmGUGE", eventType)).asJsonObject().getJsonObject("_embedded").getJsonArray("events");
    }

    public static JsonObject getLocationData(JsonObject event) {
        return event.getJsonObject("_embedded").getJsonArray("venues").getJsonObject(0);
    }

    public static String getWeatherDataOfLocationEvent(JsonObject event) {
        return readJson(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,es&lang=es&APPID=8f8dccaf02657071004202f05c1fdce0", getLocationData(event).getJsonObject("city").getString("name"))).asJsonObject().getJsonArray("weather").getJsonObject(0).getString("main");
    }
}
