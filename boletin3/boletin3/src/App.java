public class App {
    public static void main(String[] args) throws Exception {
        JsonManager jsonManager = new JsonManager();
        jsonManager.readLocationWheatherData("pontevedra");
    }
}
