import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.json.JsonObject;

public class WeatherCityData {
    public WeatherCityData(JsonObject weatherData) {
        initializaData(weatherData);
    }

    public void initializaData(JsonObject weatherData) {
        name = weatherData.getString("name");
        latitude = weatherData.getJsonObject("coord").getJsonNumber("lat").doubleValue();
        longitude = weatherData.getJsonObject("coord").getJsonNumber("lon").doubleValue();
        id = weatherData.getInt("id");
        temperature = weatherData.getJsonObject("main").getJsonNumber("temp").doubleValue() - 273;
        humidity = weatherData.getJsonObject("main").getInt("humidity");    
        percentageProbabilityOfClouds = weatherData.getJsonObject("clouds").getInt("all");
        windSpeed = weatherData.getJsonObject("wind").getJsonNumber("speed").doubleValue();
        forecast = weatherData.getJsonArray("weather").getJsonObject(0).getString("main");
        date = Instant.ofEpochSecond(weatherData.getJsonNumber("dt").longValue()).atZone(ZoneId.of("GMT+1")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s\nCoordenadas: %f || %f\nTemperatura: %.2f ºC\nHumedad: %d%%\nPercentaje de nubes: %d%%\nVelocidad del viento: %.2f Km/h\nPronostico: %s\nFecha: %s", name, latitude, longitude, temperature, humidity, percentageProbabilityOfClouds, windSpeed, forecast, date);
    }

    private String name;
    public String getName() {
        return name;
    }

    private double latitude;
    public double getLatitude() {
        return latitude;
    }

    private double longitude;
    public double getLongitude() {
        return longitude;
    }

    private int id;
    public int getId() {
        return id;
    }
    private double temperature;
    public double getTemperature() {
        return temperature;
    }
    private int humidity;
    public int getHumidity() {
        return humidity;
    }
    private int percentageProbabilityOfClouds;
    public int getProbabilityPercentageOfClouds() {
        return percentageProbabilityOfClouds;
    }
    private double windSpeed;
    public double getWindSpeed() {
        return windSpeed;
    }
    private String forecast;
    public String getForecast() {
        return forecast;
    }
    private String date;
    public String getDate() {
        return date;
    }
}
