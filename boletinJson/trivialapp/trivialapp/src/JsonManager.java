import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
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

    public static JsonArray getResults(JsonValue json) {
        return json.asJsonObject().getJsonArray("result");
    }

    public static String[] getQuestions(JsonArray jsonArray) {
        String[] questions = new String[jsonArray.size()];
        for (int i = 0; i < questions.length; i++) {
            questions[i] = jsonArray.getJsonObject(i).getString("question");
        }
        return questions;
    }

    public static String[][] getAnswers(JsonArray jsonArray) {
        String[][] answers = new String[jsonArray.size()][];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = new String[jsonArray.getJsonObject(i).getJsonArray("incorrect_answers").size() + 1];
            for (int j = 0; j < answers[i].length; j++) {
                if (j < answers[i].length - 1) {
                    answers[i][j] = String.format("%s", jsonArray.getJsonObject(i).getJsonArray("incorrect_answers").getString(j));
                } else {
                    answers[i][j] = String.format("%s*", jsonArray.getJsonObject(i).getString("correct_answer"));
                }
            }
        }
        return answers;
    }

    public static void showTrivial(String[] questions, String[][] answers) {
        if (questions.length != answers.length) {
            return;
        }
        for (int i = 0; i < answers.length; i++) {
            System.out.println(questions[i]);
            for (int j = 0; j < answers[i].length; j++) {
                System.out.println(answers[i][j]);
            }
            System.out.println();
        }
    }
}
