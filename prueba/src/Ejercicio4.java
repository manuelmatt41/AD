import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio4 {

    public static void contadorCaracter(String path) {
        File archivo = new File(path);
        int numeroOcurrencias = 0;
        char letraMax = ' ';
        ArrayList<Character> letras = new ArrayList<>();

        try (Scanner sc = new Scanner(archivo)) {

            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                
                for (int i = 0; i < linea.length(); i++) {
                    if (linea.charAt(i) != ' ') {
                        if (!letras.contains(linea.charAt(i))) {
                            letras.add(linea.charAt(i));
                        }
                    }
                }

            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        for (int i = 0; i < letras.size(); i++) {
            if (numeroOcurrencias < Ejercicio3.contadorCaracteres(path, letras.get(i))) {
                numeroOcurrencias = Ejercicio3.contadorCaracteres(path, letras.get(i));
                letraMax = letras.get(i);
            }
        }

        System.out.println(numeroOcurrencias);
        System.out.println(letraMax);
    }

    public static void main(String[] args) {
        contadorCaracter("C:\\Users\\Manuel Marín\\Documents\\aa.txt");
    }
}
