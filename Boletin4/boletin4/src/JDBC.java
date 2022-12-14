import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

        closeConnection();

        return -1;
    }

    private boolean exist(String table, String colum, Integer value) {
        if (value == null) {
            return false;
        }

        String query = String.format("SELECT EXISTS(SELECT %s FROM %s WHERE %s=%d);", colum, table, colum,value);
        int result = 0;
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                result = resultSet.getInt(String.format("EXISTS(SELECT %s FROM %s WHERE %s=%d)", colum, table, colum,value));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        closeConnection();

        return result == 1;
    }

    public boolean existStudent(Integer primaryKey) {
        return exist("alumnos", "codigo", primaryKey);
    }

    public boolean existSubject(Integer primaryKey) {
        return exist("asignaturas", "cod", primaryKey);
    }

    private ArrayList<Integer> getIntegersPrimarysKeys(String table, String colum) {
        String query = String.format("SELECT %s FROM %s ORDER BY %s;", colum, table, colum);
        ArrayList<Integer> id = new ArrayList<>();
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                id.add(resultSet.getInt(colum));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
        return id;
    }

    public ArrayList<Integer> getStudentsPrimaryKeys() {
        return getIntegersPrimarysKeys("alumnos", "codigo");
    }

    public ArrayList<Integer> getSubjectsPrimaryKeys() {
        return getIntegersPrimarysKeys("asignaturas", "cod");
    }

    public SQLStudent getRandomStudent() {
        ArrayList<Integer> id = getStudentsPrimaryKeys();
        int randomID = id.get((int) (Math.random() * id.size() + 0));
        String query = String.format("SELECT * FROM alumnos WHERE codigo=%d;", randomID);
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();

            SQLStudent student = new SQLStudent(resultSet.getInt("codigo"), resultSet.getString("nombre"),
                    resultSet.getString("apellidos"), resultSet.getInt("altura"), resultSet.getInt("aula"));
            closeConnection();

            return student;
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();

        return null;
    }

    public SQLSubject getRandomSubject() {
        ArrayList<Integer> id = getSubjectsPrimaryKeys();
        int randomID = id.get((int) (Math.random() * id.size() + 0));
        String query = String.format("SELECT * FROM asignaturas WHERE cod=%d;", randomID);
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            SQLSubject subject = new SQLSubject(resultSet.getInt("cod"), resultSet.getString("nombre"));
            closeConnection();
            
            return subject;
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();

        return null;
    }

    public void viewStudent(String name) {
        String query = String.format("SELECT * FROM alumnos WHERE nombre LIKE (\"%%%s%%\")", name);
        int countRows = 0;
        openConnection();
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

    public void addStudent(SQLStudent student) {
        if (student.getId() != null) {
            System.err.println("To add students they cannot have th ID defined");
            return;
        }

        String query = String.format(
                "INSERT INTO alumnos (nombre, apellidos, altura, aula) VALUES (\"%s\", \"%s\", %d, %d)",
                student.getName(), student.getSurname(), student.getHeight(), student.getClassId());
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Student added" : "Student not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
    }

    public void removeStudent(SQLStudent sqlStudent) {
        if (!existStudent(sqlStudent.getId())) {
            System.err.println("The student doesnt exist");
            return;
        }

        String queryNotas = String.format("DELETE FROM notas WHERE alumno=%d;", sqlStudent.getId());
        String queryStudent = String.format("DELETE FROM alumnos WHERE codigo=%d;", sqlStudent.getId());
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(queryNotas);

            System.out.printf("%s",
                    statement.executeUpdate(queryStudent) != 0 ? "Student removed" : "Student not removed");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
    }

    public void updateStudent(SQLStudent student) {
        if (!existStudent(student.getId())) {
            System.err.println("The student doesnt exist");
            return;
        }
        String query = String.format(
                "UPDATE alumnos SET nombre=IFNULL(%s, nombre), apellidos=IFNULL(%s, apellidos), altura=IFNULL(%d, altura), aula=IFNULL(%d, aula) WHERE codigo=%d;",
                student.getName(), student.getSurname(), student.getHeight(), student.getClassId(), student.getId());
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Student updated" : "Student not updated");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
    }

    public void addSubjects(SQLSubject subject) {
        if (subject.getId() != null) {
            System.err.println("To add subjects they cannot have th ID defined");
            return;
        }

        String update = String.format("INSERT INTO asignaturas (cod,nombre) VALUES (%d,\"%s\")", subject.getId(),
                subject.getName());
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(update) != 0 ? "Subject added" : "Subject not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
    }

    public void removeSubjects(SQLSubject sqlSubject) {
        if (!existSubject(sqlSubject.getId())) {
            System.err.println("The subject doesnt exist");
            return;
        }

        String query = String.format("DELETE FROM asignaturas WHERE COD=%d;", sqlSubject.getId());
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Subject removed" : "Subject not removed");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
    }

    public void updateSubject(SQLSubject subject) {
        if (!existSubject(subject.getId())) {
            System.err.println("The subject doesnt exist");
            return;
        }

        String query = String.format("UPDATE asignaturas SET nombre=IFNULL(%s, nombre) WHERE cod=%d", subject.getName(), subject.getId());
        openConnection();

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Subject updated" : "Subject not updated");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        closeConnection();
    }
}
