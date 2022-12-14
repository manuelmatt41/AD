public class SQLStudent {
    private Integer id;

    public Integer getId() {
        return id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateSqlString(name);
    }

    private String surname;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String apellidos) {
        this.surname = validateSqlString(apellidos);
    }

    private Integer height;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    private Integer classId;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public SQLStudent(Integer id, String name, String surname, Integer height, Integer classId) {
        this.id = id;
        setName(name);
        setSurname(surname);
        setHeight(height);
        setClassId(classId);
    }

    public SQLStudent(String name, String surname, int height, int classId) {
        this(null, name, surname, height, classId);
    }

    private String validateSqlString(String stringNullable) {
        return stringNullable != null ? String.format("'%s'", stringNullable) : "null";
    }
}
