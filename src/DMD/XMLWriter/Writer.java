package DMD.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

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
    private HashMap<Integer, Role> roles = new HashMap<Integer, Role>();
    private static StringBuffer sb;
    private List<Map.Entry<Integer, Role>> list;

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

    public Role linearSearch(String name) {
        for (int i = 1; i <= roles.size(); i++) {
            if (roles.get(i).getName().equals(name)) {
                roles.get(i).print();
                return roles.get(i);
            }
        }
        return null;
    }

    public void sort() {
        list = new ArrayList(roles.entrySet());
        list.sort(new NameComp());
        int i = 0;
    }

    public Role binarySearch(String name) {
        int lo = 0;
        int hi = roles.size() - 1;
        int curIn;
        while (true) {
            curIn = (hi + lo)/2;
            Role r = list.get(curIn).getValue();
            if (r.getName().equals(name))
                return r;
            else if (lo > hi) {
                System.out.println("Item is not found!");
                return null;
            }
            else {
                if (r.getName().compareTo(name) < 1)
                    lo = curIn+  1;
                else
                    hi = curIn - 1;
            }

        }
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

}
