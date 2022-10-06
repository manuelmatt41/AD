import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio3 {
    public static int contadorCaracteres(String path, char letra) {
        File archivo = new File(path);
        int contador = 0;

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();

                for (int i = 0; i < linea.length(); i++) {
                    if (linea.charAt(i) == letra) {
                        contador++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        // System.out.println(contador);
        return contador;
    }

    public static void main(String[] args) {
        contadorCaracteres("C:\\Users\\Manuel Marín\\Documents\\bb1.txt", 'e');
    }
}
