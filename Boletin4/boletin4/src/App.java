public class App {
    public static void main(String[] args) throws Exception {
        JDBC jdbc = new JDBC();
        // jdbc.getAlumnos("a");
        // jdbc.addStudent(new Student("Manuel", "Marin", 179, 20));
        // jdbc.addSubjects(new Subject(jdbc.getRowCount("cod", "asignaturas") + 1,"Matematicas"));
        // jdbc.removeStudent(9);
        jdbc.removeSubjects(2);
    }
}
