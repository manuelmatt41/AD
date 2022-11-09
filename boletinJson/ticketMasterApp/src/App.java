import javax.json.JsonArray;
import javax.json.JsonObject;

public class App {
    public static void verEventos(String eventType) {
        JsonArray events = JsonManager.getEvents(eventType);
        for (int i = 0; i < events.size(); i++) {
            JsonObject event = events.getJsonObject(i);
            JsonObject location = JsonManager.getLocationData(event);
            System.out.printf("Evento: %s\n", event.getString("name"));
            System.out.printf("  Fecha: %s\n", event.getJsonObject("dates").getJsonObject("start").getString("localDate"));
            System.out.printf("  Promotor: %s\n", event.getJsonObject("promoter").getString("name"));
            System.out.printf("  Localizacion: %s\n", location.getString("name"));
            System.out.printf("    Ciudad: %s\n", location.getJsonObject("city").getString("name"));
            System.out.printf("    Comunidad: %s\n", location.getJsonObject("state").getString("name"));
            System.out.printf("    Pais: %s\n", location.getJsonObject("country").getString("name"));
        }
    }

    public static void verEventosTiempo(String eventType) {
        JsonArray events = JsonManager.getEvents(eventType);
        for (int i = 0; i < events.size(); i++) {
            System.out.printf("Evento: %s\n  Weather: %s\n", events.getJsonObject(i).getString("name"), JsonManager.getWeatherDataOfLocationEvent(events.getJsonObject(i)));
        }
    }

    public static void main(String[] args) throws Exception {
        verEventosTiempo("sport");
    }
}
