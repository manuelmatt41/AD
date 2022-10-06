import java.io.File;

public class Ejercicio2 {

    public static void mostrarDatos(String path) {
        File directorio = new File(path);
        
        if (directorio != null) {
            if (directorio.isDirectory()) {
                File[] files = directorio.listFiles();

                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        System.out.println(files[i].getAbsolutePath());
                        
                        if (files[i].isDirectory()) {
                            mostrarDatos(files[i].getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        mostrarDatos("C:\\Users\\Manuel Marín\\Documents");
    }
}
