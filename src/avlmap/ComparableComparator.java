//@authors Alex Csorba and Julian Powell
package avlmap;

import java.util.Comparator;

public class ComparableComparator<T extends Comparable> implements Comparator<T>{

    //     Default constructor. Does nothing really.
    public ComparableComparator() {
        // nothing to do
    }

    //     Compares the two specified objects using their natural ordering.
    public int compare(T lhs, T rhs){
        if (lhs.getClass() != rhs.getClass()) {
            throw new ClassCastException();
        }
        return lhs.compareTo(rhs);
    }
}
