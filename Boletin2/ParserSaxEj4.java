import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSaxEj4 extends DefaultHandler {
    public ParserSaxEj4(int numberOfDirectors) {
        this.numberOfDirectors = numberOfDirectors;
        countOfDirectors = 0;
        element = "";
        title = "";
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "titulo":
                element = qName;
                return;
            case "director":
                countOfDirectors++;
                break;
        }
        element = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (element.equals("titulo")) {
            if (title.equals("")) {
                title = new String(ch, start, length);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("pelicula")) {
            if (countOfDirectors == numberOfDirectors) {
                System.out.printf("Title: %s\n", title);
            }
            countOfDirectors = 0;
            title = "";
        }
    }

    int numberOfDirectors;
    int countOfDirectors;
    String title;
    String element;
}
