import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("all")
public class Menu {
    public void menu() {
        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Elige una opcion:\n" +
                    "1.Dar de alta alumno\n" +
                    "2.Mostrar alumnos\n" +
                    "3.Modificar alumno\n" +
                    "4.Borrar alumno\n" +
                    "5.Salir");

            try {
                opcion = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    Alumno alumno = new Alumno();
                    alumno.pedirDatos();
                    alumno.setCodigo(ultimoCodigo() + 1);
                    guardarEstudiantes(alumno);
                    break;
                case 2:
                    mostrarEstudiantes();
                    break;
                case 3:
                    modificarEstudiante();
                    break;
                case 4:

                default:
                    break;
            }
        } while (opcion != 5);

        ;
    }

    private void guardarEstudiantes(Alumno alumno) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(main, true))) {
            dos.writeInt(alumno.getCodigo());
            dos.writeUTF(alumno.getNombre());
            dos.writeUTF(String.format("%.2f", alumno.getAltura()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int ultimoCodigo() {
        int ultimoCodigo = -1;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(main))) {
            while (true) {
                ultimoCodigo = dis.readInt();
                dis.readUTF();
                dis.readUTF();
            }
        } catch (EOFException e) {
            return ultimoCodigo;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void mostrarEstudiantes() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(main))) {
            while (true) {
                System.out.printf("%s\n",
                        new Alumno(dis.readInt(), dis.readUTF(), Double.parseDouble(dis.readUTF().replace(",", "."))));
            }
        } catch (EOFException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean modificarEstudiante() {
        int codigo = -1;
        File aux = new File(System.getProperty("user.home") + "\\aux.dat");
        do {
            codigo = pedirEntero("Escribe el codigo del alumno que quiere modificar");
        } while (codigo < 1 || codigo > ultimoCodigo());

        try (DataInputStream dis = new DataInputStream(new FileInputStream(main))) {
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(aux))) {
                Alumno alumno;
                while (true) {
                    alumno = new Alumno(dis.readInt(), dis.readUTF(),
                            Double.parseDouble(dis.readUTF().replace(",", ".")));
                    dos.writeInt(alumno.getCodigo());

                    if (alumno.getCodigo() == codigo) {
                        alumno.pedirDatos();
                    }
                    dos.writeUTF(alumno.getNombre());
                    dos.writeUTF(String.format("%.2f", alumno.getAltura()));
                }
            }
        } catch (EOFException e) {
            main.delete();
            aux.renameTo(main);
            main = aux;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private int pedirEntero(String cadena) {
        int num;
        boolean error = false;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(cadena);
            num = Integer.parseInt(sc.nextLine());
            try {

            } catch (NumberFormatException e) {
                e.printStackTrace();
                error = true;
            }
        } while (error || num < 0);

        return num;
    }

    File main = new File(System.getProperty("user.home") + "\\alumnos.dat");
}
