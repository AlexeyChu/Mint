package DMD.XMLWriter;

/**
 * Created by alex on 28.10.15.
 */
public class WriterApp {
    public static void main(String[] args) throws Exception {
        Writer w = new Writer();

        w.insert(new Employee("Alex", "Innopolis", "Innopolis"));
        w.insert(new Student("Piter", "Innopolis", "afdgqer@gmail.com"));
        w.insert(new Employee("John", "Innopolis", "TA"));
        w.insert(new Student("Adam", "Innopolis", "aqsdfer@gmail.com"));
        w.insert(new Student("Adam", "Kazan", "aqsdr@gmail.com"));
        w.insert(new Employee("Nancy", "Innopolis", "Instructor"));

        w.delete(2);

        w.updateName(1, "Michael");
        w.updateEmail(4, "12@gmail.com");
        w.updateAddress(1, "Kazan");
        w.updateDesignation(3, "TA");

        w.search("Adam");

    }
}
