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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void print() {
        System.out.println("ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Designation: " + this.getDesignation());
        System.out.println("Address: " + this.getAddress());
    }

    public String toString() {
        String tmp = "<" + "employee" + " mdate=\"2015-10-27\">\n" +
                "<id>" + this.getId() + "</id>\n" +
                "<name> " + this.getName() + " </name>\n" +
                "<designation> " + this.designation + " </designation>\n" +
                "<address> " + this.getAddress() + " </address>\n" +
                "</employee>\n\n";
        return tmp;
    }
}
