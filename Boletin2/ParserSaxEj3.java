import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSaxEj3 extends DefaultHandler {
    public ParserSaxEj3() {
        nodeName = "";
        lastNodeName = "";
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!qName.equals(lastNodeName)) {
            switch (qName) {
                case "titulo":
                    nodeName = "Titulo";
                    break;
                case "director":
                    nodeName = "Director:";
                    break;
                case "nombre":
                    nodeName = "Nombre";
                    break;
                case "apellido":
                    nodeName = "Apellido";
                    break;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (nodeName.equals(lastNodeName)) {
            return;
        }
        lastNodeName = nodeName;
        if (nodeName.equals("Director:")) {
            System.out.println(nodeName);
            return;
        }
        System.out.printf("%s-> %s\n", nodeName, new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("pelicula")) {
            System.out.println();
        }
    }

    String nodeName;
    String lastNodeName;
}
