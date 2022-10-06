import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Ejercicio7 {
    public static void sortFiles(String path, String operacion) {
        File archivo = new File(path);
        char opcion = !operacion.trim().equals("") ? operacion.trim().charAt(0) : 'z';

        switch (opcion) {
            case 'n':
                System.out.printf("El numero de lineas es %d y de palabras %d", countLines(archivo),
                        countWords(archivo));
                break;
            case 'A':
                writeSortFile(sortLinesSensitive(archivo, true));
                break;
            case 'D':
                writeSortFile(sortLinesSensitive(archivo, false));
                break;
            case 'a':
                writeSortFile(sortLinesNoSensitive(archivo, true));
                break;
            case 'd':
                writeSortFile(sortLinesNoSensitive(archivo, false));
                break;
            default:
                break;
        }
    }

    public static int countLines(File archivo) {
        int contador = 0;

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                contador++;
                sc.nextLine();
            }
        } catch (IOException e) {
            return -1;
        }

        return contador;
    }

    public static int countWords(File archivo) {
        int contador = 0;

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                String[] palabras = sc.nextLine().split(" ");

                for (int i = 0; i < palabras.length; i++) {
                    contador++;
                }
            }
        } catch (IOException e) {
            return -1;
        }

        return contador;
    }

    public static ArrayList<String> sortLinesSensitive(File archivo, boolean flag) {
        ArrayList<String> lineas = new ArrayList<>();

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                lineas.add(sc.nextLine());
            }
        } catch (IOException e) {

        }

        Collections.sort(lineas, flag ? Collections.reverseOrder() : null);

        return lineas;
    }

    public static ArrayList<String> sortLinesNoSensitive(File archivo, boolean flag) {
        ArrayList<String> lineas = new ArrayList<>();

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                lineas.add(sc.nextLine());
            }
        } catch (IOException e) {

        }

        Collections.sort(lineas, String.CASE_INSENSITIVE_ORDER);

        if (flag) {
            Collections.reverse(lineas);
        }

        return lineas;
    }

    public static void writeSortFile(ArrayList<String> lineas) {
        try (FileWriter fw = new FileWriter(System.getProperty("user.home") + "\\fichero.txt")) {
            for (int i = 0; i < lineas.size(); i++) {
                fw.write(lineas.get(i));
                fw.write("\n");
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        sortFiles(System.getProperty("user.home") + "\\Documents\\bb1.txt", "n");
    }
}
