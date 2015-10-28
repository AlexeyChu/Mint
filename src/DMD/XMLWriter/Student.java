package DMD.XMLWriter;

/**
 * Created by alex on 27.10.15.
 */
public class Student extends Role {

    private String email;

    public Student(String name, String address, String email) {
        super(name, address);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void print() {
        System.out.println("ID: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Address: " + this.getAddress());
    }

    public String toString() {
        String tmp = "<" + "student" + " mdate=\"2015-10-27\">\n" +
                "<id>" + this.getId() + "</id>\n" +
                "<name> " + this.getName() + " </name>\n" +
                "<email> " + this.email + " </email>\n" +
                "<address> " + this.getAddress() + " </address>\n" +
                "</student>\n\n";
        return tmp;
    }
}
