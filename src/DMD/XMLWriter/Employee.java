package DMD.XMLWriter;

/**
 * Created by alex on 27.10.15.
 */
public class Employee extends Role {

    private String designation;

    public Employee(String name, String address, String designation) {
        super(name, address);
        this.designation = designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String toString() {
        String tmp = "< " + "employee" + " key=\"1\" mdate=\"2015-10-27\">\n" +
                "<id>1</id>\n" +
                "<name> " + this.getName() + " </name>\n" +
                "<designation> " + this.designation + " </designation>\n" +
                "<address> " + this.getAddress() + " </address>\n" +
                "</employee>\n";
        return tmp;
    }
}
