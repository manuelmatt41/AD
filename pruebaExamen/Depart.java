import java.io.Serializable;

public class Depart implements Serializable {

    public Depart(String name, int id) {
        setName(name);
        setId(id);
    }

    public Depart() {
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
