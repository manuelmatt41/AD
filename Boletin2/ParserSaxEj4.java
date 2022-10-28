import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSaxEj4 extends DefaultHandler {
    public ParserSaxEj4(int maxDirectorsToShow) {
        nodeName = "";
        lastNodeName = "";
        directorCount = 0;
        isFilm = false;
        this.maxDirectorsToShow = maxDirectorsToShow;
        documentSave = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case "pelicula":
                    isFilm = true;
                    if (nodeName.equals("start")) {
                        nodeName = "end";
                        return;
                    }
                    nodeName = "start";
                    break;
                case "director":
                    isFilm = false;
                    directorCount++;
                    break;
            }
    }

    public void showFilm(char[] ch) {
        System.out.println(new String(ch, startFilm, startFilm + endFilm));
    }

    public void getDocument(char[] ch) {
        String[] partialDocument = new String(ch).replace("\n", " ").replace("\r", " ").replace("\t", " ").split(" ");
        document = "";
        for (String cadenas : partialDocument) {
            
            document += cadenas;
        }
        documentSave = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!documentSave) {
            getDocument(ch);
        }
        if (isFilm) { 
            if (nodeName.equals("start")) {
                startFilm = start;
                System.out.println(new String(ch, start, length));
            } else {
                if (directorCount == maxDirectorsToShow) {
                    // showFilm(ch);
                }
                endFilm = 0;
                directorCount = 0;
            }
        endFilm += length;
        }
        endFilm += length;
    }

    String nodeName;
    String lastNodeName;
    boolean isFilm;
    int directorCount;
    int maxDirectorsToShow;
    int startFilm;
    int endFilm;
    boolean documentSave;
    String document;
}
