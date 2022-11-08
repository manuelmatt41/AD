import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(JsonManager.getCitiesWeatherDatas("vigo", 5)));
    }
}