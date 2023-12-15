package session;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public interface Session<E> {

    void save(Object entity, String primaryKey);
    void close();
    Object get(Object value, String primaryKey, Object entity);

}
