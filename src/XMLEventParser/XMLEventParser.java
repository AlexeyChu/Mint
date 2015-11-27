package XMLEventParser;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Crazy on 24.11.2015.
 */
public class XMLEventParser {

    private PrintWriter writer;

    private XMLEventReader r;
    private XMLEvent e;

    private Statement statement;

    private List<String> authors;
    private String title;
    private String year;
    private String number;
    private String journal;
    private String volume;

    private Date d;

    private int n;

    public XMLEventParser() throws  Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dblp", "postgres", "postgrespass")) {

            statement = connection.createStatement();

            d = new Date();
            writer = new PrintWriter(new File("db.txt"));

            tempWriteFile("Start: " + d.toString() + "\n");

            n = 0;
            authors = new ArrayList<>();

            //createTables();
            initReader();
            parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearLists() {
        authors.clear();
        title = null;
        year = null;
        number = null;
        journal = null;
        volume = null;
    }

    private void initReader() {

        try {
            String filename = "dblp.xml";
            XMLInputFactory factory = XMLInputFactory.newInstance();
            r = factory.createXMLEventReader
                    (filename, new FileInputStream(filename));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isContainsApostroph(String s) {
        return s.contains("'");
    }

    private String getWithDoubleApostroph(String s) {
        if (isContainsApostroph(s))
            return s.replaceAll("'", "''");
        return s;
    }

    private void fillLists(String attr) throws Exception {

        XMLEvent nextE = r.nextEvent();
        String text = nextE.toString();
        System.out.println("ElementText: " + text);

        if (nextE.isCharacters()) {
            String temp = getWithDoubleApostroph(text);
            switch(attr) {
                case "<author>": authors.add(temp);
                    break;
                case "<title>": title = temp;
                    break;
                case "<year>": year = temp;
                    break;
                case "<number>": number = temp;
                    break;
                case "<journal>": journal = temp;
                    break;
                case "<volume>": volume = temp;
                    break;
            }
        }
    }

    private void tempWriteFile(String s) {
        try {
            writer.print(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parse() {

        try {
            StringBuffer text = new StringBuffer();
            while (r.hasNext()) {
                e = r.nextEvent();
                if (e.isEndElement() && e.toString().equals("</article>")) {
                    insertDB();
                    clearLists();
                }

                if (e.isStartElement()) {
                    String attr = e.toString();
                    fillLists(attr);
                }

                if (n == 1000000) {
                    System.out.println("****************");
                    System.out.println("***Million!!!***");
                    d = new Date();
                    tempWriteFile("Finish: " + d.toString() + "\n");
                    writer.close();
                    break;
                }
            }
        } catch (Exception e ) {
            String s = e.toString() + '\n' + '\n' + e.getMessage();
            tempWriteFile(s);
        }
    }

    private void createTables() throws SQLException {

            statement.executeUpdate("CREATE TABLE publication (pubid SERIAL primary key, title CHAR(1000), year INT)");
            statement.executeUpdate("CREATE TABLE author (id SERIAL primary key, name CHAR(300), articleTitle CHAR(1000))");
            statement.executeUpdate("CREATE TABLE article (pubid SERIAL primary key, title CHAR(1000), year INT, journal CHAR(200), volume INT, number INT)");
            //statement.executeUpdate("CREATE TABLE inproceeding (pubid INT, pubkey INT, title CHAR(20), years INT, booktitle CHAR(20), editor CHAR(20))");
            //statement.executeUpdate("CREATE TABLE book (pubid CHAR(20), pubkey CHAR(20), title CHAR(20), years INT, isdn CHAR(20), publisher CHAR(40))");
    }

    private void insertDB() {

            insertAuthor(authors, title);
            insertPublication(/*pubkey,*/ title, year);
            insertArticle(title, year, journal, volume, number);
            System.out.println(++n);
    }

    private void insertAuthor(List<String> authors, String title) {

        try {
            for (String name : authors) {
                String query = "INSERT INTO author (name, articleTitle) VALUES ('" + name + "', '" + title + "')";
                statement.executeUpdate(query);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    private void insertPublication(/*String pubkey,*/ String title, String year) {

        try {
            String query = "INSERT INTO publication (title, year) VALUES ('" + title + "', " + year + ")";
            statement.executeUpdate(query);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void insertArticle(String title, String year, String journal, String volume, String number) {
        String query;
        try {
            query = "INSERT INTO article (title, year, journal, volume, number) VALUES ('" + title + "', " + year + ", '" + journal + "', " + volume + ", " + number + ")";
            statement.executeUpdate(query);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
