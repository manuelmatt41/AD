public class SQLSubject {
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

    public SQLSubject(Integer id, String name) {
        this.id = id;
        setName(name);
    }

    public SQLSubject(String name) {
        this(null, name);
    }

    private String validateSqlString(String stringNullable) {
        return stringNullable != null ? String.format("'%s'", stringNullable) : "null";
    }
}
