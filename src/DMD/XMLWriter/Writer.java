package DMD.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.TreeMap;

/**
 * Created by alex on 27.10.15.
 */
public class Writer {


    private static String template = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\n" +
            "                    <!DOCTYPE roles [\n" +
            "                    <!ENTITY s \"Student\">\n" +
            "                    <!ENTITY e \"Employee\">\n" +
            "                    ]>\n";
    private static File file = new File("roles.xml");
    private TreeMap<Integer, Role> roles;

    public void insert(Role role) throws Exception {
        roles.put(role.getId(), role);
        updateFile();

    }

    public Role delete(Object key) throws Exception{
        Role role = roles.remove(key);
        updateFile();
        return role;
    }

    public void updateName(Role role, String name) throws Exception{
        role.setName(name);
        roles.put(role.getId(), role);
        updateFile();
    }

    public void updateEmail(Role role, String email) throws Exception{
        ((Student)role).setEmail(email);
        roles.put(role.getId(), role);
        updateFile();
    }

    public void updateAddress(Role role, String address) throws Exception{
        role.setAddress(address);
        roles.put(role.getId(), role);
        updateFile();
    }

    public void updateDesignation(Role role, String designation) throws Exception{
        ((Employee)role).setDesignation(designation);
        roles.put(role.getId(), role);
        updateFile();
    }

    public Role search(Role role) {
        return roles.get(role.getId());
    }

    public void updateFile() throws Exception {
        print(template);
        for (int i = 0; i < roles.size(); i++) {
            print(roles.get(i).toString());
        }
    }

    public static void print(String value) throws Exception {
        FileChannel fc = new FileOutputStream(file).getChannel();
        fc.write(ByteBuffer.wrap(value.getBytes()));
        fc.close();
    }

    public static String getTable(Role role) throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            String doc = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\n" +
                    "<!DOCTYPE roles [\n" +
                    "<!ENTITY s \"Student\">\n" +
                    "<!ENTITY e \"Employee\">\n" +
                    "]>\n" +

                    "<roles>\n" +
                    "<student key=\"1\" mdate=\"2015-10-27\">\n" +
                    "<id>1</id>\n" +
                    "<name>Alexey Chuvatkin</name>\n" +
                    "<email>a.chuvatkin@innopolis.ru</email>\n" +
                    "<address>Russia, Republic of Tatarstan, Innopolis city, Sportivnaya str., 6</address>\n" +
                    "</student>\n" +

                    "<employee key=\"1\" mdate=\"2015-10-27\">\n" +
                    "<id>1</id>\n" +
                    "<name>Sadegh Nobari</name>\n" +
                    "<designation>Senior Research Scientist at Innopolis University</designation>\n" +
                    "<address>Russia, Republic of Tatarstan, Innopolis city, Sportivnaya str., unknown</address>\n" +
                    "</employee>\n" +
                    "</roles>\n";
            pw.print(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
