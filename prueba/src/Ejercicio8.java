import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio8 {

    public static void copiarArchivosBinariosBuffer(File in, File out, int tamanyoBuffer) {
        try (FileInputStream fr = new FileInputStream(in)) {
            BufferedInputStream br = new BufferedInputStream(fr);

            try (FileOutputStream fo = new FileOutputStream(out)) {
                BufferedOutputStream bw = new BufferedOutputStream(fo, tamanyoBuffer);
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
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        copiarArchivosBinariosBuffer(new File(PATH + "\\bb1.txt"), new File(PATH + "\\cc.txt"), 1000);
        // copiarArchivoBinario(new File(PATH + "\\bb1.txt"), new File(PATH + "\\cc.txt"));
    }

    private static final String PATH = System.getProperty("user.home") + "\\Documents";
}
