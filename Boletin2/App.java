import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class App {

    public static Document createDomTree(File file) {
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

    public static void showFilms(Document domTree) {
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

    public static boolean showFilmsTittles(Document domTree) {
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

    public static void showDocument(Node node, int tabulaciones) {
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

    public static void main(String[] args) {
        Document doc = createDomTree(filmFile);
        showDocument(doc.getFirstChild(), 4);
    }

    public static File filmFile = new File(
            System.getProperty("user.home") + "\\Documents\\Acceso a datos\\Boletin2\\Peliculas.xml");
}