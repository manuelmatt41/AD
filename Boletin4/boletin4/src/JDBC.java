import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    private Connection connection;
    private String database = "add";
    private String server = "localhost";
    private String user = "root";
    private String password = "";

    public void openConnection() {
        try {
            String url = String.format("jdbc:mariadb://%s:3306/%s", server, database);
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getLocalizedMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Código error: " + e.getErrorCode());

        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing the connection: " + e.getLocalizedMessage());
        }
    }

    public int getRowCount(String column, String table) {
        openConnection();
        String query = String.format("SELECT COUNT(%s) FROM %s", column, table);
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(String.format("COUNT(%s)", column));
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        return -1;
    }

    public void getStudent(String name) {
        openConnection();
        String query = String.format("SELECT * FROM alumnos WHERE nombre LIKE (\"%%%s%%\")", name);
        int countRows = 0;
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.printf("%s\n", resultSet.getString("nombre"));
                countRows++;
            }
            System.out.println(String.format("%d rows affected", countRows));
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        closeConnection();
    }

    public void addStudent(Student student) {
        openConnection();
        String query = String.format(
                "INSERT INTO alumnos (nombre, apellidos, altura, aula) VALUES (\"%s\", \"%s\", %d, %d)",
                student.getName(), student.getSurname(), student.getHeight(), student.getClassId());
        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Student added" : "Student not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        closeConnection();
    }

    public void removeStudent(int id) {
        openConnection();
        String queryNotas = String.format("DELETE FROM notas WHERE alumno=%d;", id);
        String queryStudent = String.format("DELETE FROM alumnos WHERE codigo=%d;", id);
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(queryNotas);
            System.out.printf("%s",
                    statement.executeUpdate(queryStudent) != 0 ? "Student removed" : "Student not removed");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        closeConnection();
    }

    public void updateStudent(Student student) {
        openConnection();
        String query = String.format("UPDATE alumnos SET codigo=%d, nombre=%s, apellidos=%s, altura=%d, aula=%d;",
                student.getId(), student.getName(), student.getSurname(), student.getHeight(), student.getClassId());
        try (Statement statement = this.connection.createStatement()) {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void addSubjects(Subject subject) {
        openConnection();
        String update = String.format("INSERT INTO asignaturas (cod,nombre) VALUES (%d,\"%s\")", subject.getId(),
                subject.getName());
        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(update) != 0 ? "Subject added" : "Subject not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        closeConnection();
    }

    public void removeSubjects(int id) {
        openConnection();
        String query = String.format("DELETE FROM asignaturas WHERE COD=%d;", id);
        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Subject removed" : "Subject not removed");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
