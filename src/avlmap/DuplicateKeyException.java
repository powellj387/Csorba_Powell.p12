//@authors Alex Csorba and Julian Powell
package avlmap;

import java.io.Serializable;

public class DuplicateKeyException extends RuntimeException implements Serializable {

    public DuplicateKeyException() {
        super();
    }

    public DuplicateKeyException(String message) {
        super(message);
    }
}
