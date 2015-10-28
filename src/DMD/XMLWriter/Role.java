package DMD.XMLWriter;

import java.util.TreeSet;

/**
 * Created by alex on 27.10.15.
 */
public class Role {
    private int id;
    private String name;
    private String address;
    private static int counter = 0;

    public Role(String name, String address) {
        this.id = ++counter;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void print() {
        System.out.println("ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Address: " + this.getAddress());
    }

    @Override
    public int hashCode() {
        return id;
    }

}
