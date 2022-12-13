import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ejercicio4 {
    public void getMaxOcurrenciesOfLetters(File in) {
        HashMap<Character, Integer> map = new HashMap<>();
        // try (FileReader fr = new FileReader(in)) {
        // int i;
        // while ((i = fr.read()) != -1) {
        // if (!map.containsKey((char) i)) {
        // map.put((char) i, 1);
        // } else {
        // map.put((char) i, map.get((char) i) + 1);
        // }
        // }
        try (Scanner sc = new Scanner(in)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != ' ') {
                        map.merge(line.charAt(i), 1, Integer::sum);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        int maxValue = Collections.max(map.values());
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxValue) {
                System.out.printf("El caracter %c se usa %d veces\n", entry.getKey(), maxValue);
            }
        }
    }

    public static void main(String[] args) {
        Ejercicio4 ej = new Ejercicio4();
        File file = new File("C:\\Users\\manue\\Documents\\DAM\\AD\\pruebaExamen\\a.txt");
        ej.getMaxOcurrenciesOfLetters(file);
    }

}