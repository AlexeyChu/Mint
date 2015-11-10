package Parser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;

/**
 * Created by Crazy on 27.09.2015.
 **/

public class Parser {

    public static void main(String[] args) throws Exception {
        //Class.forName("org.postgresql.Driver");
        File file = new File("D://Parsers//Parsing//AMT13.xml");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "postgrespass");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document docDom = db.parse(file);
        System.out.println("Root element :" + docDom.getDocumentElement().getNodeName());

        // Document docDom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        System.out.println(docDom.toString() + " : " + file);
        NodeList list = docDom.getElementsByTagName("author");

        String appInsertForamt = "INSERT INTO app(%s) VALUES (%s);";
        String[] attributes = {"author", "title"};

        String[] values = new String[attributes.length];
        for (int i = 0; i < list.getLength(); i++) {
            System.out.println(list.item(i));
            NamedNodeMap map = list.item(i).getAttributes();
            for (int j = 0; j < attributes.length; j++) {
                values[j] = map.getNamedItem(attributes[j]).toString().replaceFirst(".*=", "");
                System.out.println(values[j]);

            }
            String cols = Arrays.toString(attributes).replaceAll("\\[|\\]", "");
            String vals = Arrays.toString(values).replaceAll("\\[|\\]", "");
            String query = String.format(appInsertForamt, cols, vals);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            //statement.

        }

    }
}
