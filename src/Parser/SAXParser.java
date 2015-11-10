package Parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Crazy on 30.09.2015.
 */
public class SAXParser {

    public static XMLStreamReader parser;
    public static String content;
    public static String attribute;
    static int n;

    public static void main(String[] args) {

        try (InputStream in = new FileInputStream("D://Parsers//Parsing//dblp.xml");
             Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project_test", "postgres", "postgrespass")) {

            XMLInputFactory factory = XMLInputFactory.newInstance();
            parser = factory.createXMLStreamReader(in);

            String[] attributes = {"author"/*, "publication", "inproceeding", "book"*/};
            Statement statement = connection.createStatement();

           /*  for (int i = 0; i < attributes.length; i++) {
                statement.executeUpdate("CREATE TABLE " + attributes[i] + " (Message CHAR(5000))");
            }*/
            n = 0;

            while (parser.hasNext()) {
                parseAttr("author");
                if (n == 1000000)
                    break;
            }
            System.out.println(n);
        }
        catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void parseAttr(String attr) throws Exception {

        int event = parser.next();

        if (event == XMLStreamConstants.START_ELEMENT &&
                ((attribute = parser.getLocalName()).equals("sup") || attribute.equals("sub") || attribute.equals("i") ||
                        attribute.equals("tt") || attribute.equals(attr))) {
           // attribute = parser.getLocalName();
            event = parser.next();
            if (event == XMLStreamConstants.CHARACTERS)
                content += parser.getText();
        }
        if (event == XMLStreamConstants.END_ELEMENT && parser.getLocalName().equals(attr)) {
            n++;
            System.out.println(attr + " : " + content);
            content = "";
        }
    }
}
