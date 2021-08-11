package local.netts3s.utils;

import local.netts3s.entity.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public List<Track> parseFromFile(String sourcePath) {
        File xmlFile = new File(sourcePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List<Track> tracks = new ArrayList<>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Node");
            tracks = new ArrayList<Track>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                tracks.add(getTrackFromNode(nodeList.item(i)));
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        //tracks.forEach(System.out::println);
        return tracks;
    }

    private static Track getTrackFromNode(Node node) {
        Track track = new Track();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            track.setName(getTagValue("Track", element));
        }
        return track;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        NodeList list = nodeList.item(0).getChildNodes();
        if (list.getLength() > 0) {
            return list.item(0).getNodeValue();
        } else {
            return "";
        }
    }
}
