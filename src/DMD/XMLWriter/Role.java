package DMD.XMLWriter;

import java.util.TreeSet;

/**
 * Created by alex on 27.10.15.
 */
public class Role {
    private int id;
    private String name;
    private String address;
    private static int counter;
    //private String type;

    public Role(String name, String address) {
        counter++;
        this.id = counter;
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

    @Override
    public int hashCode() {
        return id;
    }

}
