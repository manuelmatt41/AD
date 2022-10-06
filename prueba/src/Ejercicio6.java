import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio6 {

    public static void separarFicheroCaracteres(int numeroCaracteres, File archivo) {
        char[] buffer = new char[numeroCaracteres];
        int contador = 0;

        try (FileReader fr = new FileReader(archivo)) {
            while (fr.read(buffer) != -1) {
                try (FileWriter fw = new FileWriter(
                        new File(System.getProperty("user.home") + "\\fichero" + contador + ".txt"))) {
                    fw.write(new String(buffer, 0, buffer.length));
                } catch (IOException e) {

                }
                contador++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void separarFicheroLineas(int numeroFilas, File archivo) {
        int contador = 0;

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                try (FileWriter fw = new FileWriter(
                        new File(System.getProperty("user.home") + "\\fichero" + contador + ".txt"))) {
                    for (int i = 0; i < numeroFilas; i++) {
                        if (sc.hasNextLine()) {
                            fw.write(sc.nextLine());
                            fw.write(sc.hasNextLine() ? "\n" : "");
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

                contador++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void unionFicheros(File... ficheros) {
        for (int i = 0; i < ficheros.length; i++) {
            try (Scanner sc = new Scanner(ficheros[i])) {
                while (sc.hasNext()) {
                    try (FileWriter fw = new FileWriter((System.getProperty("user.home") + "\\ficheroUnido.txt"),
                            true)) {
                        fw.write(sc.nextLine());
                        fw.write("\n");
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        separarFicheroCaracteres(10000, new File(System.getProperty("user.home") +
                "\\Documents\\aa.txt"));
        // separarFicheroLIneas(1000, new File(System.getProperty("user.home") +
        // "\\Documents\\aa.txt"));
        // unionFicheros(new File(System.getProperty("user.home") +
        // "\\Documents\\aa.txt"),
        // new File(System.getProperty("user.home") + "\\Documents\\aa.txt"));
    }
}
