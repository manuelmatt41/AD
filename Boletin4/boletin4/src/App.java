public class App {
    public static void getTime(JDBC jdbc, int n) {
        jdbc.openConnection();
        long initTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            jdbc.viewApprovedStudentSubjects();
        }
        long finalTime = System.currentTimeMillis();
        System.out.println((finalTime - initTime) + " miliseconds");
        jdbc.closeConnection();
    }

    public static void main(String[] args) throws Exception {
        JDBC jdbc = new JDBC();
        jdbc.openConnection();
        // SQLStudent student = jdbc.getRandomStudent();
        // SQLSubject sqlSubject = jdbc.getRandomSubject();

        // jdbc.viewStudent(student.getName(false));
        // jdbc.addStudent(new Student("Manuel", "Marin", 179, 20));
        // jdbc.addSubjects(new Subject(jdbc.getRowCount("cod", "asignaturas") +
        // 1,"Matematicas"));
        // jdbc.removeStudent(9);
        // jdbc.removeSubjects(2);
        // student.setName(null);
        // jdbc.updateStudent(student);
        // sqlSubject.setName("asdfsd");
        // jdbc.updateSubject(sqlSubject);
        // jdbc.viewClassWithStudents();
        // jdbc.viewClassesWithoutStudents();
        // jdbc.viewStudentName("m%", 5);
        // jdbc.addColum("asignaturas", "algofgdfgg", "varar(50)", "not null default
        // 'espero que salga bien'");
        // jdbc.viewDatabaseData();
        // jdbc.viewCatalogs();
        // jdbc.viewDBTables();
        // jdbc.viewDBViews();
        // jdbc.viewAllDBTables();
        // jdbc.viewSavedProcedures();
        jdbc.viewDBTables("a%");
        jdbc.closeConnection();

        // getTime(jdbc, 1);
        // getTime(jdbc, 10);
        // getTime(jdbc, 100);
        // getTime(jdbc, 1000);
        // getTime(jdbc, 10000);
        // getTime(jdbc, 100000);
        // getTime(jdbc, 1000000);
        // getTime(jdbc, 10000000);
        // getTime(jdbc, 100000000);
    }
}
