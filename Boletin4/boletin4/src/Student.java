public class Student {
    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String surname;

    public String getSurname() {
        return surname;
    }
    public void setSurname(String apellidos) {
        this.surname = apellidos;
    }

    private int height;

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    
    private int classId;

    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Student(String name, String surname, int height, int classId) {
        setName(surname);
        setSurname(surname);
        setHeight(height);
        setClassId(classId);
    }
}
