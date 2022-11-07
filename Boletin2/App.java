import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class App {
    // Ejercicio 1
    public Document createDomTree(File file) {
        Document doc = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(file);
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public Document createDomTree() {
        DocumentBuilderFactory docFactory;
        DocumentBuilder docBuilder = null;
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return docBuilder.newDocument();
    }

    // Ejercicio 3
    public void showFilms(Document domTree) {
        NodeList filmList = domTree.getElementsByTagName("pelicula");
        NodeList directorNodeList;
        NodeList titleList;
        NodeList directorName;
        NodeList directorSurname;
        Node title;
        Node name;
        Node surname;

        for (int i = 0; i < filmList.getLength(); i++) {
            titleList = ((Element) filmList.item(i)).getElementsByTagName("titulo");
            directorNodeList = ((Element) filmList.item(i)).getElementsByTagName("director");

            for (int j = 0; j < titleList.getLength(); j++) {
                title = titleList.item(j);
                System.out.println(title.getNodeName() + "->" + title.getFirstChild().getNodeValue());
            }

            for (int j = 0; j < directorNodeList.getLength(); j++) {
                directorName = ((Element) directorNodeList.item(j)).getElementsByTagName("nombre");
                directorSurname = ((Element) directorNodeList.item(j)).getElementsByTagName("apellido");

                for (int k = 0; k < directorName.getLength(); k++) {
                    name = directorName.item(k);
                    System.out.println(name.getNodeName() + "->" + name.getFirstChild().getNodeValue());
                }

                for (int k = 0; k < directorSurname.getLength(); k++) {
                    surname = directorSurname.item(k);
                    System.out.println(surname.getNodeName() + "->" + surname.getFirstChild().getNodeValue());
                }
            }
        }

    }

    // Ejercicio 2
    public boolean showFilmsTittles(Document domTree) {
        NodeList nodeList = domTree.getElementsByTagName("titulo");
        Node node;

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("Titulo ->" + node.getFirstChild().getNodeValue());
            }
        }

        return true;

    }

    // Ejercicio 4
    public void showDocument(Node node, int tabulaciones) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < tabulaciones; i++) {
            if (i == tabulaciones - 2) {
                System.out.print(node.getNodeType());
                continue;
            }
            System.out.print(" ");
        }
        System.out.println(node.getNodeName());
        if (node.hasChildNodes()) {
            showDocument(node.getFirstChild(), tabulaciones + 4);
        } else {
            Node sibling = node.getNextSibling();
            int tab = sibling != null ? tabulaciones : tabulaciones - 4;
            showDocument(sibling != null ? sibling : node.getParentNode().getNextSibling(), tab);
        }
    }

    // Ejercicio 5
    public void showMaxDirectors(Document domTree, int numberOfDirectors) {
        NodeList films = domTree.getElementsByTagName("pelicula");
        NodeList directors;
        NodeList auxList;
        Node aux;
        for (int i = 0; i < films.getLength(); i++) {
            directors = ((Element) films.item(i)).getElementsByTagName("director");
            if (directors.getLength() >= numberOfDirectors) {
                for (int j = 0; j < directors.getLength(); j++) {
                    auxList = directors.item(j).getChildNodes();
                    for (int k = 0; k < auxList.getLength(); k++) {
                        aux = auxList.item(k);
                        if (aux.getNodeType() == Node.ELEMENT_NODE) {
                            if (aux.getNodeName().equals("nombre")) {
                                System.out.print("Director -> ");
                                System.out.printf("%s ", aux.getFirstChild().getNodeValue());
                            }
                            if (aux.getNodeName().equals("apellido")) {
                                System.out.printf("%s\n", aux.getFirstChild().getNodeValue());
                            }
                        }
                    }
                }
            }
        }
    }

    // Ejercicio 6
    public void showGender(Document domTree) {
        NodeList films = domTree.getElementsByTagName("pelicula");
        Node film;
        Element film2;
        Node atrib;
        NamedNodeMap atribs;
        ArrayList<String> differentGenders = new ArrayList<>();
        for (int i = 0; i < films.getLength(); i++) {
            film = films.item(i);
            // film2=(Element)films.item(i);
            // if (film2.hasAttribute("genero") && !differentGenders.contains(film2.getAttribute("genero"))){
            //     differentGenders.add(film2.getAttribute("genero"));
            // }
            if (film.hasAttributes()) {
                atribs = film.getAttributes();
                for (int j = 0; j < atribs.getLength(); j++) {
                    atrib = atribs.item(j);
                    if (atrib.getNodeName().equals("genero")) {
                        System.out.printf("El atributo %s es %s\n", atrib.getNodeName(), atrib.getNodeValue());
                        if (!differentGenders.contains(atrib.getNodeValue())) {
                            differentGenders.add(atrib.getNodeValue());
                        }
                    }
                }
            }
        }
        System.out.printf("Hay un total de %d generos distintos", differentGenders.size());
    }

    // Ejercicio 7
    public void addAtribToTitle(Document domTree, String title, String atribName, String atribValue) {
        NodeList films = domTree.getElementsByTagName("titulo");
        Node film = getNode(films, title);
        if (film != null) {
            ((Element) film).setAttribute(atribName, atribValue);
            grabarDOM(domTree, "C:\\Users\\Manue\\Documents\\DAM\\AD\\Boletin2\\Peliculas.xml");
        }
    }

    public void removeAtribToTitle(Document domTree, String title, String atribname) {
        NodeList films = domTree.getElementsByTagName("titulo");
        Node film = getNode(films, title);
        if (film != null) {
            ((Element) film).removeAttribute(atribname);
            grabarDOM(domTree, "C:\\Users\\Manue\\Documents\\DAM\\AD\\Boletin2\\Peliculas.xml");
        }
    }

    public Node getNode(NodeList nodeList, String value) {
        Node node;
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getFirstChild().getNodeValue().equals(value)) {
                return node;
            }
        }
        return null;
    }

    // public searchFilm(Document domTree, String mainValue) {
    // NodeList films = domTree.getElementsByTagName("pelicula");
    // for (int i = 0; i < array.length; i++) {
    // mainValue
    // }
    // }

    public void grabarDOM(Document document, String ficheroSalida) {
        DOMImplementationRegistry registry = null;
        try {
            registry = DOMImplementationRegistry.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e1) {
            e1.printStackTrace();
        }
        DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");
        LSOutput output = ls.createLSOutput();
        output.setEncoding("UTF-8");
        try {
            output.setByteStream(new FileOutputStream(ficheroSalida));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LSSerializer serializer = ls.createLSSerializer();
        serializer.setNewLine("\r\n");
        serializer.getDomConfig().setParameter("format-pretty-print", true);
        serializer.write(document, output);
    }

    // Ejercicio 8
    /**
     * Crea y añade al elemento raiz del XML un nodo "pelicula"
     * 
     * @param domTree Arbols DOM
     * @return Devuelve el nodo "pelicula", si no contiene
     *         ningun nodo raiz devuelve null
     */
    public Element addFilm(Document domTree) {
        return (Element) domTree.getFirstChild().appendChild(domTree.createElement("pelicula"));
    }

    /**
     * Crea y añade al elemento raiz del XML un nodo "pelicula", y a este mismo un
     * nodo titulo con su respectivo valor.
     * 
     * @param domTree Arbol DOM
     * @param title   Valor del nodo titulo
     * @return Devuelve el nodo "pelicula", si no contiene
     *         ningun nodo raiz devuelve null
     */
    public Element addFilm(Document domTree, String title) {
        Element eFilm = addFilm(domTree);
        Element eTitle = domTree.createElement("titulo");
        eTitle.appendChild(domTree.createTextNode(title));
        eFilm.appendChild(eTitle);
        return eFilm;
    }

    /**
     * Crea y añade al elemento raiz del XML un nodo "pelicula", y a este mismo los
     * nodos "titulo" y "director" con sus respectivos valores
     * 
     * @param domTree         Arbol DOM
     * @param title           Valor del nodo titulo
     * @param directorName    Valor del nodo "nombre" de "director"
     * @param directorSurname Valor del nodo "apellido" de "director"
     * @return Devuelve el nodo "pelicula", si no contiene
     *         ningun nodo raiz devuelve null
     */
    public Element addFilm(Document domTree, String title, String directorName, String directorSurname) {
        Element eFilm = addFilm(domTree, title);
        Element eDirector = domTree.createElement("director");
        Element eDirectorName = domTree.createElement("nombre");
        Element eDirectorSurname = domTree.createElement("apellido");
        eDirectorName.appendChild(domTree.createTextNode(directorName));
        eDirectorSurname.appendChild(domTree.createTextNode(directorSurname));
        eDirector.appendChild(eDirectorName);
        eDirector.appendChild(eDirectorSurname);
        eFilm.appendChild(eDirector);
        return eFilm;
    }

    /**
     * Crea y añade al elemento raiz del XML un nodo "pelicula", y a este mismo los
     * nodos "titulo" y "director" con sus respectivos valores. Y añade al nodo
     * "pelicula" los atributos correspondientes
     * 
     * @param domTree         Arbol DOM
     * @param title           Valor del nodo titulo
     * @param directorName    Valor del nodo "nombre" de "director"
     * @param directorSurname Valor del nodo "apellido" de "director"
     * @param atribsNames     Nombre de los atributos
     * @param atribsValues    Valor de los atributos
     * @return Devuelve el nodo "pelicula", si no contiene
     *         ningun nodo raiz devuelve null
     */
    public Element addFilm(Document domTree, String title, String directorName, String directorSurname,
            String[] atribsNames, String[] atribsValues) {
        if (atribsNames.length != atribsValues.length) {
            throw new IllegalArgumentException();
        }
        Element eFilm = addFilm(domTree, title, directorName, directorSurname);
        for (int i = 0; i < atribsNames.length; i++) {
            eFilm.setAttribute(atribsNames[i], atribsValues[i]);
        }
        return eFilm;
    }

    /**
     * Crea y añade al elemento raiz del XML un nodo "pelicula", y a este mismo los
     * nodos "titulo" y "director" con sus respectivos valores. Y añade al nodo
     * "pelicula" los atributos correspondientes
     * 
     * @param domTree         Arbol DOM
     * @param title           Valor del nodo titulo
     * @param directorName    Valor del nodo "nombre" de "director"
     * @param directorSurname Valor del nodo "apellido" de "director"
     * @param atribsNames     Nombre de los atributos
     * @param atribsValues    Valor de los atributos
     * @param out             Archivo donde se va guardar en nuevo arbol
     *                        DOM
     * @return Devuelve el nodo "pelicula", si no contiene
     *         ningun nodo raiz devuelve null
     */
    public Element addFilm(Document domTree, String title, String directorName, String directorSurname,
            String[] atribsNames, String[] atribsValues, File out) {
        Element eFilm = addFilm(domTree, title, directorName, directorSurname, atribsNames, atribsValues);
        grabarDOM(domTree, out.getAbsolutePath());
        return eFilm;
    }

    // Ejercicio 9
    public void changeDirector(Document domTree, String oldName, String newName, String oldSurname, String newSurname) {
        Node name = getNode(domTree.getElementsByTagName("nombre"), oldName);
        Node surname = getNode(domTree.getElementsByTagName("apellido"), oldSurname);
        if (name == null || surname == null) {
            return;
        }
        if (surname.getFirstChild().getNodeValue().equals(oldSurname)) {
            name.getFirstChild().setNodeValue(newName);
            if (!newSurname.equals("")) {
                surname.getFirstChild().setNodeValue(newSurname);
            }
        }
        grabarDOM(domTree, System.getProperty("user.home") + "\\Documents\\DAM\\AD\\Boletin2\\Peliculas.xml");
    }
//TODO Arreglar el cambio de director
    public void changeDirectorName(Document domTree, String oldName, String newName, String surname) {
        changeDirector(domTree, oldName, newName, surname, "");
    }

    // Ejercicio 10
    public void addDirector(Document domTree, Node newDirector, Node title, Node director) {
        if (title.getParentNode().isSameNode(director.getParentNode())) {
            title.getParentNode().appendChild(newDirector);
            grabarDOM(domTree, filmFile.getAbsolutePath());
        }
    }

    public Node createDirector(Document domTree, String name, String surname) {
        Element director = domTree.createElement("director");
        Element nameE = domTree.createElement("nombre");
        Element surnameE = domTree.createElement("apellido");
        nameE.appendChild(domTree.createTextNode(name));
        surnameE.appendChild(domTree.createTextNode(surname));
        director.appendChild(nameE);
        director.appendChild(surnameE);
        return director;
    }

    // Ejercicio 11
    public void removeMovie(Document domTree, String title) {
        Node removedFilm = getNode(domTree.getElementsByTagName("titulo"), title);
        if (removedFilm != null) {
            domTree.getFirstChild().removeChild(removedFilm.getParentNode());
            grabarDOM(domTree, filmFile.getAbsolutePath());
        }
    }

    public Element createCompanya(Document domTree) {
        return domTree.createElement("compañia");
    }

    public Element createEmpleado(Document domTree, String name) {
        Element empleado = domTree.createElement("empleado");
        Element nameElement = domTree.createElement("nombre");
        nameElement.appendChild(domTree.createTextNode(name));
        empleado.appendChild(nameElement);
        return empleado;
    }

    public Element createEmpleado(Document domTree, String name, String surname) {
        Element empleado = createEmpleado(domTree, name);
        Element surnameElement = domTree.createElement("apellido");
        surnameElement.appendChild(domTree.createTextNode(surname));
        empleado.appendChild(surnameElement);
        return empleado;
    }

    public Element createEmpleado(Document domTree, String name, String surname, String nickname) {
        Element empleado = createEmpleado(domTree, name, surname);
        Element nicknameElement = domTree.createElement("apodo");
        nicknameElement.appendChild(domTree.createTextNode(nickname));
        empleado.appendChild(nicknameElement);
        return empleado;
    }

    public Element createEmpleado(Document domTree, String name, String surname, String nickname,
            int salary) {
        Element empleado = createEmpleado(domTree, name, surname, nickname);
        Element salaryElement = domTree.createElement("salario");
        salaryElement.appendChild(domTree.createTextNode(Integer.toString(salary)));
        empleado.appendChild(salaryElement);
        return empleado;
    }

    public Element createEmpleado(Document domTree, String name, String surname, String nickname,
            int salary, String[] atribNames, String[] atribvalues) {
        if (atribNames.length == atribvalues.length) {
            Element empleado = createEmpleado(domTree, name, surname, nickname, salary);
            for (int i = 0; i < atribvalues.length; i++) {
                empleado.setAttribute(atribNames[i], atribvalues[i]);
            }
            return empleado;
        }
        return null;
    }

    public void addEmpleado(Document domTree, Element root, Element empleado) {
        root.appendChild(empleado);
        domTree.appendChild(root);
        grabarDOM(domTree, filmFile2.getAbsolutePath());
    }

    public static void main(String[] args) {
        App app = new App();
        Document doc = app.createDomTree(filmFile);
        Document doc2 = app.createDomTree();
        // long startTime = System.currentTimeMillis();
        // long endTime;
        // endTime = System.currentTimeMillis() - startTime;
        // System.out.println(endTime););
        app.addFilm(doc, "aa", "aa", "aa", new String[] {"aa"}, new String[] {"aa"}, filmFile);
        app.addEmpleado(doc2, app.createCompanya(doc2), app.createEmpleado(doc2, "Juan", "López Pérez", "Juanín"));
    }

    public static File filmFile = new File(
            System.getProperty("user.home") + "\\Documents\\AD\\Boletin2\\Peliculas.xml");
    public static File filmFile2 = new File(
            System.getProperty("user.home") + "\\Documents\\AD\\Boletin2\\Compañia.xml");
}