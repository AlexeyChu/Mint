package Parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
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

public class StAX {

    public static XMLStreamReader parser;
    public static StringBuffer content = new StringBuffer();
    public static String attribute;
    static int n;
    static Statement statement;
    static boolean isAttr = false;

    public static void main(String[] args) {

        try (InputStream in = new FileInputStream("D://Parsers//Parsing//dblp.xml");
             Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project_test", "postgres", "postgrespass")) {

            XMLInputFactory factory = XMLInputFactory.newInstance();
            parser = factory.createXMLStreamReader(in);

            statement = connection.createStatement();
            createTables();

            n = 0;
            while (parser.hasNext()) {
                parse("author");
                //fillTablesName("author");
                if (n == 1000000)
                    break;
            }
            //System.out.println(n);
        }
        catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTables() throws SQLException {

        //statement.executeUpdate("CREATE TABLE publication (pubid INT, pubkey INT, title CHAR(20), years INT)");
        statement.executeUpdate("CREATE TABLE author (id INT primary key, name CHAR(300), homepage CHAR(1000))");
        //statement.executeUpdate("CREATE TABLE article (pubid INT, pubkey INT, title CHAR(20), years INT, journal CHAR(20), month INT, volume INT, number INT)");
        //statement.executeUpdate("CREATE TABLE inproceeding (pubid INT, pubkey INT, title CHAR(20), years INT, booktitle CHAR(20), editor CHAR(20))");
        //statement.executeUpdate("CREATE TABLE book (pubid CHAR(20), pubkey CHAR(20), title CHAR(20), years INT, isdn CHAR(20), publisher CHAR(40))");

    }

    public static void fillTablesName(String attr, String value) throws SQLException, XMLStreamException {

        String query = "INSERT INTO " + attr + " (id, name) VALUES (" + n + ", '" + value + "')";
        statement.executeUpdate(query);
        System.out.println(query);
    }

    public static boolean isContainsApostroph(String s) {
        return s.contains("'");
    }

    public static void parse(String attr) throws SQLException, XMLStreamException {
        int event = parser.next();

        if (event == XMLStreamConstants.START_ELEMENT &&
                ((attribute = parser.getLocalName()).equals("sup") || attribute.equals("sub") || attribute.equals("i") ||
                        attribute.equals("tt") || attribute.equals(attr))) {
            if (attribute.equals(attr))
                isAttr = true;
            event = parser.next();
            if (event == XMLStreamConstants.CHARACTERS && isAttr)
                //content += parser.getText();
                content.append(parser.getText());
        }

        if (event == XMLStreamConstants.END_ELEMENT && parser.getLocalName().equals(attr)) {
            isAttr = false;
            n++;
            if (isContainsApostroph(content.toString())) {
                content = new StringBuffer(content.toString().replaceAll("'", "''"));

            }

            fillTablesName(attr, content.toString());
            //System.out.println(attr + " : " + content);
            content = new StringBuffer();
        }
    }
}
