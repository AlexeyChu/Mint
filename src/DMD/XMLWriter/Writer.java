package DMD.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by alex on 27.10.15.
 */
public class Writer {

    public static void main(String[] args) {

    }

    public static void write() throws IOException {
        try (PrintWriter pw = new PrintWriter(new File("output.xml"))) {
            String title = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            pw.print(title);
            String docType = "<!DOCTYPE role [";
            pw.print(docType);
            String entitys = "<!ENTITY s \"Student\">\n<!ENTITY e \"Employee\">";
            pw.print(entitys);
            /*String entityEmpl = "<!ENTITY e \"Employee\">";
            pw.print(entityEmpl);*/

        }
    }
}
