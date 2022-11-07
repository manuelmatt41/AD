public class App {
    public static void main(String[] args) {
        JsonManager jsonManager = new JsonManager();
        // jsonManager.readLocationWheatherData("pontevedra");
        // jsonManager.readLocationWheatherData(-8.72264, 42.232819);
        // jsonManager.readLocationWheatherData(-8.72264, 42.232819, 2);
        // jsonManager.readLocationWheatherData(-8.72264, 42.232819);
        // System.out.println(jsonManager.getLocationName(-8.72264, 42.232819));
        // System.out.println(jsonManager.getCoordinatesFromLocation("pontevedra"));
        System.out.println(jsonManager.getWheatherDataDate("dt", "vigo"));
    }
}