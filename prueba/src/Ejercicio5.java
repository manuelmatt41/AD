import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio5 {

    public static void mostrarCadena(String path, String cadena) {
        File archivo = new File(path);
        ArrayList<String> lineas = new ArrayList<>();
        ArrayList<Integer> numeroLineas = new ArrayList<>();
        int contadorLineas = 0;

        try (Scanner sc = new Scanner(archivo)) {

            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] palabras = linea.split(" ");

                for (int i = 0; i < palabras.length; i++) {
                    if (cadena.equalsIgnoreCase(palabras[i])) {
                        lineas.add(linea);
                        numeroLineas.add(contadorLineas);
                        contadorLineas++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (lineas.size() > 0) {
            for (int i = 0; i < lineas.size(); i++) {
                System.out.println(lineas.get(i));
                System.out.println("linea nº: " + numeroLineas.get(i));
                System.out.println(contadorLineas);
            }
        }
    }

    public static void main(String[] args) {
        mostrarCadena("C:\\Users\\Manuel Marín\\Documents\\bb1.txt", "sancho");
    }
}
