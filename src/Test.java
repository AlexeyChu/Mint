import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by alex on 26.10.15.
 */
public class Test {
    public static void main(String[] args) {
        TreeSet set = new TreeSet();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(6);
        set.add(7);
        set.add(8);
        set.add(9);
        set.add(10);
        int i = 0;
        //Collections.b;
        Iterator it = set.iterator();
        while(it.hasNext()) {
            i++;
            if (it.next().equals(9)) {
                System.out.println(i);
                return;
            }

        }

        System.out.println("A".hashCode());
        System.out.println("B".hashCode());
        System.out.println("C".hashCode());
    }
}

