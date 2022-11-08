import javax.json.JsonArray;

public class App {
    public static void main(String[] args) throws Exception {
        JsonArray trivial = JsonManager.readJson("https://opentdb.com/api.php?amount=20&category=18&difficulty=hard").asJsonObject().getJsonArray("results");
        JsonManager.showTrivial(JsonManager.getQuestions(trivial), JsonManager.getAnswers(trivial));
    }
}
