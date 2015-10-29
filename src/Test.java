import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by alex on 26.10.15.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Date d = new Date();
        long now = d.getTime();
        BufferedReader reader = new BufferedReader(new FileReader(new File("bst.in")));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
        d = new Date();
        long after = d.getTime();
        System.out.println("Time: " + (after - now));
    }
}

