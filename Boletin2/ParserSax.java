import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSax extends DefaultHandler {
    public ParserSax() {
        isTitle = false;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        isTitle = qName.equals("titulo");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isTitle) {
            System.out.println(new String(ch, start, length));
            isTitle = false;
        }
    }

    boolean isTitle;
}
