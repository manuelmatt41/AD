public class App {
    public static void main(String[] args) throws Exception {
        JDBC jdbc = new JDBC();
        SQLStudent student = jdbc.getRandomStudent();
        SQLSubject sqlSubject = jdbc.getRandomSubject();
        // jdbc.viewStudent(student.getName());
        // jdbc.addStudent(new Student("Manuel", "Marin", 179, 20));
        // jdbc.addSubjects(new Subject(jdbc.getRowCount("cod", "asignaturas") + 1,"Matematicas"));
        // jdbc.removeStudent(9);
        // jdbc.removeSubjects(2);
        // student.setName(null);
        // jdbc.updateStudent(student);
        sqlSubject.setName("asdfsd");
        jdbc.updateSubject(sqlSubject);
    }
}
