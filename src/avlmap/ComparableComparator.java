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
        int returnValue = 0;
        if (lhs == null && rhs == null) {
            returnValue = 0; // Both are null, consider them equal.
        } else if (lhs == null) {
            returnValue = -1; // Only lhs is null, consider lhs less than rhs.
        } else if (rhs == null) {
            returnValue = 1; // Only rhs is null, consider lhs greater than rhs.
        } else {
            returnValue = lhs.compareTo(rhs); // Compare using natural ordering.
        }
        return returnValue;
    }
}
