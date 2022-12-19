import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

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

    private boolean exist(String table, String colum, Integer value) {
        if (value == null) {
            return false;
        }

        String query = String.format("SELECT EXISTS(SELECT %s FROM %s WHERE %s=%d);", colum, table, colum, value);
        int result = 0;

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                result = resultSet
                        .getInt(String.format("EXISTS(SELECT %s FROM %s WHERE %s=%d)", colum, table, colum, value));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

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

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                id.add(resultSet.getInt(colum));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

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

        return null;
    }

    public SQLSubject getRandomSubject() {
        ArrayList<Integer> id = getSubjectsPrimaryKeys();
        int randomID = id.get((int) (Math.random() * id.size() + 0));
        String query = String.format("SELECT * FROM asignaturas WHERE cod=%d;", randomID);

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            SQLSubject subject = new SQLSubject(resultSet.getInt("cod"), resultSet.getString("nombre"));
            closeConnection();

            return subject;
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        return null;
    }

    public void viewStudent(String name) {
        String query = String.format("SELECT * FROM alumnos WHERE nombre LIKE ('%%%s%%')", name);
        int countRows = 0;

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.printf("%-10s%-10s%-5d%-4d\n", resultSet.getString("nombre"),
                        resultSet.getString("apellidos"), resultSet.getInt("altura"), resultSet.getInt("aula"));
                countRows++;
            }

            System.out.println(String.format("%d rows affected", countRows));
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewStudentName(String patternName, Integer minHeight) {
        String query = "SELECT nombre FROM alumnos WHERE nombre LIKE (?) AND altura>?;";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, patternName);
            preparedStatement.setInt(2, minHeight);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.printf("%s\n", resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void addStudent(SQLStudent student) {
        if (student.getId() != null) {
            System.err.println("To add students they cannot have th ID defined");
            return;
        }

        String query = String.format(
                "INSERT INTO alumnos (nombre, apellidos, altura, aula) VALUES (\"%s\", \"%s\", %d, %d)",
                student.getName(), student.getSurname(), student.getHeight(), student.getClassId());

        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(query);
            // System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Student added" : "Student not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void removeStudent(SQLStudent sqlStudent) {
        if (!existStudent(sqlStudent.getId())) {
            System.err.println("The student doesnt exist");
            return;
        }

        String queryNotas = String.format("DELETE FROM notas WHERE alumno=%d;", sqlStudent.getId());
        String queryStudent = String.format("DELETE FROM alumnos WHERE codigo=%d;", sqlStudent.getId());

        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(queryNotas);

            System.out.printf("%s",
                    statement.executeUpdate(queryStudent) != 0 ? "Student removed" : "Student not removed");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void updateStudent(SQLStudent student) {
        if (!existStudent(student.getId())) {
            System.err.println("The student doesnt exist");
            return;
        }
        String query = String.format(
                "UPDATE alumnos SET nombre=IFNULL(%s, nombre), apellidos=IFNULL(%s, apellidos), altura=IFNULL(%d, altura), aula=IFNULL(%d, aula) WHERE codigo=%d;",
                student.getName(), student.getSurname(), student.getHeight(), student.getClassId(), student.getId());

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Student updated" : "Student not updated");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void addSubjects(SQLSubject subject) {
        if (subject.getId() != null) {
            System.err.println("To add subjects they cannot have th ID defined");
            return;
        }

        String update = String.format("INSERT INTO asignaturas (cod,nombre) VALUES (%d,\"%s\")", subject.getId(),
                subject.getName());

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(update) != 0 ? "Subject added" : "Subject not added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void removeSubjects(SQLSubject sqlSubject) {
        if (!existSubject(sqlSubject.getId())) {
            System.err.println("The subject doesnt exist");
            return;
        }

        String query = String.format("DELETE FROM asignaturas WHERE COD=%d;", sqlSubject.getId());

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Subject removed" : "Subject not removed");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void updateSubject(SQLSubject subject) {
        if (!existSubject(subject.getId())) {
            System.err.println("The subject doesnt exist");
            return;
        }

        String query = String.format("UPDATE asignaturas SET nombre=IFNULL(%s, nombre) WHERE cod=%d", subject.getName(),
                subject.getId());

        try (Statement statement = this.connection.createStatement()) {
            System.out.printf("%s", statement.executeUpdate(query) != 0 ? "Subject updated" : "Subject not updated");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewClassesWithStudents() {
        String query = "SELECT aulas.NOMBREAula FROM alumnos JOIN aulas ON aulas.numero=alumnos.aula WHERE aulas.nombreAula IS NOT NULL GROUP BY aula;";

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.printf("%s\n", resultSet.getString("nombreAula"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewApprovedStudentSubjects() {
        String query = "SELECT alumnos.nombre, asignaturas.nombre AS nombreAsig, NOTA FROM notas JOIN alumnos ON alumnos.codigo=notas.alumno JOIN asignaturas ON asignaturas.COD=notas.asignatura WHERE nota>=5;";

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // System.out.printf("%-15s%-40s%-15d\n", resultSet.getString("nombre"),
                // resultSet.getString("nombreAsig"),
                // resultSet.getInt("nota"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

    }

    public void viewClassesWithoutStudents() {
        String query = " SELECT nombre FROM asignaturas WHERE cod!=ALL(SELECT asignatura FROM notas);";

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.printf("%s\n", resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void addColum(String table, String newColumName, String dataType, String properties) {
        String query = String.format("ALTER TABLE %s ADD %s %s %s;", table, newColumName, dataType, properties);

        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("The column added");
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
            System.out.println("The column not added");
        }
    }

    public void viewDatabaseData() {
        try {
            DatabaseMetaData dbmt = this.connection.getMetaData();
            System.out.printf(
                    "Driver name: %s\nDriver version: %s\nUrl: %s\nConnected user: %s\nDBMS: %s\nDBMS version: %s\nDBMS keywords: %s\n",
                    dbmt.getDriverName(), dbmt.getDriverVersion(), dbmt.getURL(), dbmt.getUserName(),
                    dbmt.getDatabaseProductName(), dbmt.getDatabaseProductVersion(), dbmt.getSQLKeywords());
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewCatalogs() {
        try {
            ResultSet catalogs = this.connection.getMetaData().getCatalogs();

            while (catalogs.next()) {
                System.out.printf("%s\n", catalogs.getString("TABLE_CAT"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewDBTables() {
        try {
            ResultSet tables = this.connection.getMetaData().getTables(this.database, null, null, null);

            while (tables.next()) {
                System.out.printf("%-15s%-10s\n", tables.getString("TABLE_NAME"), tables.getString("TABLE_TYPE"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewDBTables(String patternNameTable) {
        try {
            ResultSet columns = this.connection.getMetaData().getColumns(this.database, null, patternNameTable, null);

            while (columns.next()) {
                System.out.printf(
                        "Column index: %d\nDatabse: %s\nTable: %s\nColumn name: %s\nData type: %s\nColumn size: %d\nIs nullable: %s\n",
                        columns.getInt("ORDINAL_POSITION"), this.database, columns.getString("TABLE_NAME"),
                        columns.getString("COLUMN_NAME"), columns.getString("TYPE_NAME"), columns.getInt("COLUMN_SIZE"),
                        columns.getString("IS_NULLABLE"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void viewDBViews() {
        try {
            ResultSet views = this.connection.getMetaData().getTables(this.database, null, null,
                    new String[] { "VIEW" });

            while (views.next()) {
                System.out.printf("%s\n", views.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewAllDBTables() {
        try {
            DatabaseMetaData dbmt = this.connection.getMetaData();
            ResultSet catalogs = dbmt.getCatalogs();
            ResultSet tables;
            String db;
            while (catalogs.next()) {
                db = catalogs.getString("TABLE_CAT");
                System.out.printf("%s:\n", db);
                tables = dbmt.getTables(db, null, null, null);

                while (tables.next()) {
                    System.out.printf("%-50s%-10s\n", tables.getString("TABLE_NAME"), tables.getString("TABLE_TYPE"));
                }

                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewSavedProcedures() {
        try {
            ResultSet procedures = this.connection.getMetaData().getProcedures(this.database, null, null);

            while (procedures.next()) {
                System.out.printf("%s\n", procedures.getString("PROCEDURE_NAME"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewPrimaryKeys() {
        try {
            ResultSet primaryKeys = this.connection.getMetaData().getPrimaryKeys(this.database, null, null);
            ResultSet exportedKeys = this.connection.getMetaData().getExportedKeys(this.database, null, null);

            while (primaryKeys.next()) {
                System.out.printf("%s: %s (%s)\n", primaryKeys.getString("PK_NAME"),
                        primaryKeys.getString("COLUMN_NAME"), primaryKeys.getString("TABLE_NAME"));
            }

            while (exportedKeys.next()) {
                System.out.printf("%s: %s (%s)\n", exportedKeys.getString("FK_NAME"),
                        exportedKeys.getString("FKCOLUMN_NAME"), exportedKeys.getString("PKTABLE_NAME"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewColumnDatas() {
        String query = "SELECT *, nombre AS non FROM alumnos";

        try (Statement statement = this.connection.createStatement()) {
            ResultSetMetaData resultSetMetaData = statement.executeQuery(query).getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.printf("%-20s%-20s%-20s%-8b%-8b\n", resultSetMetaData.getColumnName(i),
                        resultSetMetaData.getColumnLabel(i), resultSetMetaData.getColumnTypeName(i),
                        resultSetMetaData.isAutoIncrement(i), resultSetMetaData.isNullable(i));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void viewAvaibleDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement().getClass());
        }
    }

    public void getImages() {
        String query = "SELECT * from imagenes;";

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                saveImages(resultSet.getBinaryStream("imagen"),
                        new File(System.getProperty("user.home") + "\\" + resultSet.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void addImages(File in) {
        String query = "INSERT INTO imagenes (nombre, imagen) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            FileInputStream fis = new FileInputStream(in);
            preparedStatement.setString(1, in.getName());
            preparedStatement.setBinaryStream(2, fis, fis.available());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getErrorCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImages(InputStream imageStream, File out) {
        try (InputStream is = imageStream) {
            try (FileOutputStream fos = new FileOutputStream(out)) {
                fos.write(is.readAllBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAulas(int minPuestos, String patterName) {
        ResultSet resultSet;
        try (CallableStatement cs = this.connection.prepareCall("CALL getAulas(?, ?)")) {
            cs.setInt(1, minPuestos);
            cs.setString(2, patterName);
            resultSet = cs.executeQuery();

            while (resultSet.next()) {
                System.out.printf("%-4d%-20s%-5d\n", resultSet.getInt("numero"), resultSet.getString("nombreaula"),
                        resultSet.getInt("puestos"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }

        try (Statement statement = this.connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT SUMA()");

            while (resultSet.next()) {
                System.out.printf("Puestos totales: %d\n", resultSet.getInt("SUMA()"));
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public void getText(String database, String pattern) {
        DatabaseMetaData databaseMetaData;
        ResultSet tables;
        ResultSet columns;
        ResultSet result;
        String query;

        try (Statement statement = this.connection.createStatement()) {
            databaseMetaData = this.connection.getMetaData();
            tables = databaseMetaData.getTables(database, null, null, null);

            while (tables.next()) {
                columns = databaseMetaData.getColumns(null, null, tables.getString("TABLE_NAME"), null);

                while (columns.next()) {
                    if (isColumnTextDataType(columns.getString("TYPE_NAME"))) {
                        query = String.format("SELECT %s FROM %s WHERE %s LIKE ('%s')",
                                columns.getString("COLUMN_NAME"), tables.getString("TABLE_NAME"),
                                columns.getString("COLUMN_NAME"), pattern);
                        result = statement.executeQuery(query);

                        while (result.next()) {
                            System.out.printf("Database:%s  Column:%s  Column:%s\n", database,
                                    tables.getString("TABLE_NAME"),
                                    columns.getString("COLUMN_NAME"));
                            System.out.println(result.getString(columns.getString("COLUMN_NAME")));
                            System.err.println();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
        }
    }

    public boolean isColumnTextDataType(String columnName) {
        return columnName.equals("CHAR") || columnName.equals("VARCHAR");
    }

    public void exportTableToXML(String tableName, String objectNames, File out) {
        Document doc = createDOMTree();
        Element root = doc.createElement(tableName);
        Element row;
        Element col;
        ResultSet resultSet;
        ResultSetMetaData columns;
        String query = String.format("SELECT * FROM %s", tableName);

        try (Statement statement = this.connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            columns = resultSet.getMetaData();

            while (resultSet.next()) {
                row = doc.createElement(objectNames);

                for (int i = 1; i <= columns.getColumnCount(); i++) {
                    col = doc.createElement(columns.getColumnName(i));
                    col.setTextContent(resultSet.getString(columns.getColumnLabel(i)));
                    row.appendChild(col);
                }
                root.appendChild(row);
            }
            doc.appendChild(root);
            saveDOMTree(doc, out.getAbsolutePath());
        } catch (SQLException e) {
            System.out.println("Error in: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public Document createDOMTree() {
        Document doc = null;
        try {
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            factoria.setIgnoringComments(true);
            DocumentBuilder builder = factoria.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (Exception e) {
            System.out.println("Error generando el árbol DOM: " + e.getMessage());
        }
        return doc;
    }

    public void saveDOMTree(Document document, String ficheroSalida) {
        DOMImplementationRegistry registry;
        try {
            registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");
            LSOutput output = ls.createLSOutput();
            output.setEncoding("UTF-8");
            output.setByteStream(new FileOutputStream(ficheroSalida));
            LSSerializer serializer = ls.createLSSerializer();
            serializer.setNewLine("\r\n");
            serializer.getDomConfig().setParameter("format-pretty-print", true);
            serializer.write(document, output);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}