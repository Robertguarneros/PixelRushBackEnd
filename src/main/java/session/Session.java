package session;

import org.eclipse.persistence.internal.expressions.ObjectExpression;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public interface Session<E> {

    void save(Object entity, String primaryKey);
    void close();
    Object get(Class theClass, String pk, Object value);
    List<Object> getList(Class theClass, String key, Object value);
    List<Object> getListAll(Class theClass);
    Object getMatch(Class theClass, Object value);
    void update(Class theClass, String pk, String set, Object pkValue, Object setValue) throws SQLException;

}
