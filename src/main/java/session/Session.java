package session;

import java.util.HashMap;
import java.util.List;


public interface Session<E> {

    void save(Object entity);
    void close();

}
