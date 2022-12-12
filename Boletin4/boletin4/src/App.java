public class App {
    public static void main(String[] args) throws Exception {
        JDBC jdbc = new JDBC();
        // jdbc.getAlumnos("a");
        // jdbc.addAlumnos("Manuel", "Marin", 179, 20);
        jdbc.addSubjects(jdbc.getRowCount("cod", "asignaturas"),"Matematicas");
    }
}
