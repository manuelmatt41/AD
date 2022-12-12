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

    public void getAlumnos(String name) {
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

    public void addAlumnos(String name, String surname, int height, int classNumber) {
        openConnection();
        String update = String.format(
                "INSERT INTO alumnos (nombre, apellidos, altura, aula) VALUES (\"%s\", \"%s\", %d, %d)",
                name, surname, height, classNumber);
        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(update) != 0 ? "Student added" : "Student not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        closeConnection();
    }

    public void addSubjects(int cod, String name) {
        openConnection();
        String update = String.format("INSERT INTO asignaturas (cod,nombre) VALUES (%d,\"%s\")", cod + 1, name);
        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(update) != 0 ? "Subject added" : "Subject not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
        closeConnection();
    }
}
