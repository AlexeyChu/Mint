package DMD.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alex on 27.10.15.
 */
public class Writer {

    private static String template = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\n" +
            "<!DOCTYPE roles [\n" +
            "<!ENTITY s \"Student\">\n" +
            "<!ENTITY e \"Employee\">\n" +
            "]>\n\n" +
            "<roles>\n\n";
    private static File file = new File("roles.xml");
    private TreeMap<Integer, Role> roles = new TreeMap<>();
    private static StringBuffer sb;

    public void insert(Role role) throws Exception {
        roles.put(role.getId() - 1, role);
        updateFile();

    }

    public Role delete(int key) throws Exception{
        Role role = roles.remove(key-1);
        updateFile();
        return role;
    }

    public void updateName(Integer key, String name) throws Exception{
        roles.get(key - 1).setName(name);
        updateFile();
    }

    public void updateEmail(Integer key, String email) throws Exception{
        try {
            Role role = roles.get(key - 1);
            ((Student) role).setEmail(email);
            //roles.put(role.getId(), role);
            updateFile();
        } catch (Exception e) {
            System.out.println("Employee has not email! You can't change it.");
        }
    }

    public void updateAddress(Integer key, String address) throws Exception{
        Role role = roles.get(key - 1);
        role.setAddress(address);
        updateFile();
    }

    public void updateDesignation(Integer key, String designation) throws Exception {
        try {
            Role role = roles.get(key - 1);
            ((Employee) role).setDesignation(designation);
            updateFile();
        } catch (ClassCastException e) {
            System.out.println("Student cannot be cast to Employee! You can't change it!");
        }
    }

    public Role search(String name) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getName().equals(name)) {
                roles.get(i).print();
                return roles.get(i);
            }
        }
        return null;
    }

    public void updateFile() throws Exception {
        sb = new StringBuffer();
        sb.append(template);
        for (Map.Entry<Integer, Role> entry : roles.entrySet()) {
            sb.append(entry.getValue().toString());
        }
        sb.append("</roles>\n");
        print(sb.toString());
    }

    public static void print(String value) throws Exception {
        FileChannel fc = new FileOutputStream(file).getChannel();
        fc.write(ByteBuffer.wrap(value.getBytes()));
        fc.close();
    }

   /* public static String getTable(Role role) throws IOException {
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
        }*/

}
