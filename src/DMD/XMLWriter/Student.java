package DMD.XMLWriter;

/**
 * Created by alex on 27.10.15.
 */
public class Student extends Role {
    private String email;
    //final String type = "student";


    public Student(String name, String address, String email) {
        super(name, address);
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        String tmp = "< " + "student" + " key=\"1\" mdate=\"2015-10-27\">\n" +
                "<id>1</id>\n" +
                "<name> " + this.getName() + " </name>\n" +
                "<email> " + this.email + " </email>\n" +
                "<address> " + this.getAddress() + " </address>\n" +
                "</student>\n";
        return tmp;
    }
}
