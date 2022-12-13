import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {

    public static boolean addAll(File out) {
        for (int i = 0; i < 10; i++) {
            try (ObjectOutputStream os = out.length() == 0 ? new ObjectOutputStream(new FileOutputStream(out))
                    : new AppendingObjectOutputStream(new FileOutputStream(out, true))) {
                os.writeObject(new Persona("Manuel", i));
                os.writeObject(new Depart("Informatica", i));
            } catch (EOFException e) {
                System.err.println("Fin del fichero");
            } catch (IOException e) {
                System.err.println("No se ha encotrado el fichero");
                return false;
            }
        }

        return true;
    }

    public static boolean viewAll(File in) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(in))) {
            while (true) {
                Object obj = ois.readObject();

                if (obj instanceof Persona) {
                    Persona p = (Persona) obj;
                    System.out.printf("Persona: Nombre: %s Id: %s\n", p.getName(), p.getId());
                }

                if (obj instanceof Depart) {
                    Depart d = (Depart) obj;
                    System.out.printf("Depart: Nombre: %s Id: %s\n", d.getName(), d.getId());
                }
            }
        } catch (EOFException e) {
            System.err.println("Fin del fichero");
        } catch (IOException e) {
            System.err.println("No se ha encotrado el fichero");
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("No se ha encontrado la clase");
            return false;
        }

        return true;
    }

    public static boolean remove(File in, File out, int fileIndex) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(in))) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(out));
                    ObjectOutputStream apObject = new AppendingObjectOutputStream(new FileOutputStream(out))) {
                Object obj = ois.readObject();

                if (obj instanceof Persona) {
                    Persona p = (Persona) obj;
                    if (p.getId() != fileIndex) {
                        apObject.writeObject(p);
                    }
                }

                if (obj instanceof Depart) {
                    Depart d = (Depart) obj;
                    if (d.getId() != fileIndex) {
                        apObject.writeObject(d);
                    }
                }
                while (true) {
                    obj = ois.readObject();

                    if (obj instanceof Persona) {
                        Persona p = (Persona) obj;
                        if (p.getId() != fileIndex) {
                            apObject.writeObject(p);
                        }
                    }

                    if (obj instanceof Depart) {
                        Depart d = (Depart) obj;
                        if (d.getId() != fileIndex) {
                            apObject.writeObject(d);
                        }
                    }

                }
            } catch (EOFException e) {
                System.err.println("Fin del fichero");

                return true;
            } catch (ClassNotFoundException e) {
                return false;
            }
        } catch (EOFException e) {

        } catch (IOException e) {
            return false;
        }

        return false;
    }

    public static void start() {
        Scanner sc = new Scanner(System.in);
        int option;
        boolean error = false;
        File file = new File(System.getProperty("user.home") + "\\ejercicio10.txt");

        do {
            System.out.println("Elige una opcion:\n" +
                    "1.Incluir personas y departamentos\n" +
                    "4.Salir");
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                error = true;
                option = -1;
            }

            switch (option) {
                case 1:
                    addAll(file);
                    break;
                case 2:
                    System.err.println(file.exists());
                    viewAll(file);
                    break;
                case 3:
                    remove(file, new File(System.getProperty("user.home") + "\\aux.txt"), 5);
                default:
                    break;
            }

        } while (option != 4 || error);
    }

    public static void main(String[] args) {
        start();
    }
}