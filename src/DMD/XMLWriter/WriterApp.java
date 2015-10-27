package DMD.XMLWriter;

/**
 * Created by alex on 28.10.15.
 */
public class WriterApp {
    public static void main(String[] args) throws Exception{
        Writer w = new Writer();
        w.insert(new Student("Alex", "Innopolis", "aqer@gmail.com"));
        w.insert(new Student("Piter", "Innopolis", "afdgqer@gmail.com"));
        w.insert(new Employee("John", "Innopolis", "TA"));
        w.insert(new Student("Adam", "Innopolis", "aqsdfer@gmail.com"));
        w.insert(new Employee("Nancy", "Innopolis", "Instructor"));


    }
}
