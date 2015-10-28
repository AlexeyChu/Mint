package DMD.XMLWriter;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by alex on 28.10.15.
 */
class NameComp implements Comparator<Map.Entry<Integer, Role>> {

    @Override
    public int compare(Map.Entry<Integer, Role> r1, Map.Entry<Integer, Role> r2) {
        return r1.getValue().getName().compareTo(r2.getValue().getName());
    }
}
