import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSaxEj2 extends DefaultHandler {
    public ParserSaxEj2() {
    }
    
    @Override
    public void startDocument() throws SAXException {
        System.out.println("comienzo a leer");
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.printf("<%s>", qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.print(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.err.printf("</%s>", qName);
    }

}