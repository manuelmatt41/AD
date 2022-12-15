public class SQLStudent {
    private Integer id;

    public Integer getId() {
        return id;
    }

    private String name;

    public String getName() {
        return getSQLString(name, false);
    }

    public String getNameSqlSintax() {
        return getSQLString(name, true);
    }

    public void setName(String name) {
        this.name = validateString(name);
    }

    private String surname;

    public void setSurname(String apellidos) {
        this.surname = validateString(apellidos);
    }

    public String getSurname() {
        return getSQLString(surname, false);
    }

    public String getSurnameSqlSintax() {
        return getSQLString(surname, true);
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

    private String validateString(String stringNullable) {
        return stringNullable != null ? stringNullable : "null";
    }

    private String getSQLString(String sqlString, boolean isSqlSintax) {
        return isSqlSintax && !sqlString.equals("null") ? String.format("'%s'", sqlString) : sqlString;
    }
}
