import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Sax {
    // Ejercicio 13
    public void getSax(String xmlPath) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        ParserSaxEj4 parserSax = new ParserSaxEj4(2);
        try {
            parser.parse(new File(xmlPath), parserSax);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Sax sax = new Sax();
        sax.getSax("C:\\Users\\Manuel Marín\\Documents\\AD\\Boletin2\\Peliculas.xml");
    }
}
