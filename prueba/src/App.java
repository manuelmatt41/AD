import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class App {
    // #region Ejercicio1
    public static void mostrarFicheros(String path) {
        File directorio = new File(path);

        if (directorio.isDirectory()) {
            File[] archivos = directorio.listFiles();
            ArrayList<File> directorios = new ArrayList<>();
            System.out.println("Archivos:");

            for (int i = 0; i < archivos.length; i++) {
                if (archivos[i].isFile()) {
                    System.out.printf("%s\n", archivos[i].getName());
                } else {
                    directorios.add(archivos[i]);
                }
            }

            System.out.println("");
            System.out.println("Directorios:");

            for (int i = 0; i < directorios.size(); i++) {
                if (archivos[i].isDirectory()) {
                    System.out.printf("%s\n", directorios.get(i).getName());
                }
            }
        }
    }

    // #endregion
    // #region Ejercicio2
    public static void mostrarArbolFicheros(String path) {
        File directorio = new File(path);

        if (directorio != null) {
            if (directorio.isDirectory()) {
                File[] files = directorio.listFiles();

                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        System.out.println(files[i].getAbsolutePath());

                        if (files[i].isDirectory()) {
                            mostrarFicheros(files[i].getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    // #endregion
    // #region Ejercicio3
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
        System.out.printf("Hay %d de letras %c", contador, letra);
        return contador;
    }

    // #endregion
    // #region Ejercicio4
    public static void caracterMasUsado(String path) {
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
            if (numeroOcurrencias < contadorCaracteres(path, letras.get(i))) {
                numeroOcurrencias = contadorCaracteres(path, letras.get(i));
                letraMax = letras.get(i);
            }
        }

        System.out.println(numeroOcurrencias);
        System.out.println(letraMax);
    }

    // #endregion
    // #region Ejercicio5
    public static void mostrarLineas(String path, String cadena) {
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
                    }
                }
                contadorLineas++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (lineas.size() > 0) {
            for (int i = 0; i < lineas.size(); i++) {
                System.out.println(lineas.get(i));
                System.out.println("linea nº: " + numeroLineas.get(i));
            }
        }
    }

    // #endregion
    // #region Ejercicio6
    public static void separarFicheroCaracteres(int numeroCaracteres, File archivo) {
        char[] buffer = new char[numeroCaracteres];
        int contador = 0;

        int i;
        try (FileReader fr = new FileReader(archivo)) {
            while ((i = fr.read(buffer)) != -1) {
                try (FileWriter fw = new FileWriter(
                        new File(System.getProperty("user.home") + "\\fichero" + contador + ".txt"))) {
                    fw.write(buffer, 0, i);
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
                    for (int i = 0; i < numeroFilas && sc.hasNextLine(); i++) {
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

    public static void unionFicheros(File[] ficheros) {
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

    // #endregion
    // #region Ejercicio7
    public static void sortFiles(String path, String operacion) {
        File archivo = new File(path);
        char opcion = !operacion.trim().equals("") ? operacion.trim().charAt(0) : 'z';

        switch (opcion) {
            case 'n':
                System.out.printf("El numero de lineas es %d y de palabras %d", countLines(archivo, true),
                        countWords(archivo));
                break;
            case 'A':
                writeSortFile(sortLines(false, true));
                break;
            case 'D':
                writeSortFile(saveLines(false, false));
                break;
            case 'a':
                writeSortFile(saveLines(true, true));
                break;
            case 'd':
                writeSortFile(saveLines(true, false));
                break;
            default:
                break;
        }
    }

    public static int countLines(File archivo, boolean flag) {
        int contadorLineas = 0;
        int contadorPalabras = 0;

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                contadorLineas++;
                Scanner sc2 = new Scanner(sc.nextLine());
                while (sc2.hasNext()) {
                    contadorPalabras++;
                }
            }
        } catch (IOException e) {
            return -1;
        }

        return flag ? contadorLineas : contadorPalabras;
    }

    public static int countWords(File archivo) {
        return countLines(archivo, false);
    }


    public static ArrayList<String> sortLines(boolean sensitive, boolean reverse) {
        ArrayList<String> lineas = saveLines();

        if (sensitive) {
            Collections.sort(lineas, String.CASE_INSENSITIVE_ORDER);
        } else {
            Collections.sort(lineas);
        }

        if (reverse) {
            Collections.reverse(lineas);
        }

        return lineas;
    }

    public static ArrayList<String> saveLines(File archivo) {
        ArrayList<String> lineas = new ArrayList<>();

        try (Scanner sc = new Scanner(archivo)) {
            while (sc.hasNext()) {
                lineas.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    // #endregion
    // #region Ejercicio8
    public static void copiarArchivosBinariosBuffer(File in, File out) {
        try (FileInputStream fr = new FileInputStream(in)) {
            BufferedInputStream br = new BufferedInputStream(fr);

            try (FileOutputStream fo = new FileOutputStream(out)) {
                BufferedOutputStream bw = new BufferedOutputStream(fo);
                int i;
                while ((i = br.read()) != -1) {
                    bw.write(i);
                    bw.flush();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void copiarArchivoBinario(File in, File out) {
        try (FileInputStream fr = new FileInputStream(in)) {
            try (FileOutputStream fo = new FileOutputStream(out)) {
                int i;
                while ((i = fr.read()) != -1) {
                    fo.write(i);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }0
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // #endregion
    public static void main(String[] args) {

    }
}
