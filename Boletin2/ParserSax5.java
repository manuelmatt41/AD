import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSax5 extends DefaultHandler {
    public ParserSax5() {
        genders = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("pelicula")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (attributes.getLocalName(i).equals("genero")) {
                    if (!genders.contains(attributes.getValue(i))) {
                        genders.add(attributes.getValue(i));
                    }
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.printf("Hay %d generos distintos", genders.size());
    }

    ArrayList<String> genders;
}
