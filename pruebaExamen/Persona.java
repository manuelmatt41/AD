import java.io.Serializable;

public class Persona implements Serializable {
    public Persona(String name, int id) {
        setName(name);
        setId(id);
    }

    public Persona() {
        this("", 0);
    }

    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
