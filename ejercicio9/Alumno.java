import java.util.Scanner;

@SuppressWarnings("all")
public class Alumno {
    public Alumno(int codigo, String nombre, double altura) {
        setCodigo(codigo);
        setNombre(nombre);
        setAltura(altura);
    }

    public void pedirDatos() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Escribe el nombre del alumno");
            setNombre(sc.nextLine());
            System.out.println("Escribe la altura del alumno");
            setAltura(Double.parseDouble(sc.nextLine()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("Codigo: %d -- Nombre: %s -- Altura: %.2f m", codigo, nombre, altura);
    }

    public Alumno() {
        this(0, " ", 0);
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNombre(String nombre) {
        if (!nombre.equals("")) {
            this.nombre = nombre;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setAltura(double altura) {
        if (altura > 0) {
            this.altura = altura;
        }
    }

    public double getAltura() {
        return altura;
    }

    private int codigo;
    private String nombre;
    private double altura;
}
