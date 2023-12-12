package session;
import java.sql.*;
import java.sql.Connection;

public class SessionImpl {
    private final Connection conn;

    public SessionImpl(Connection connnection){this.conn = connnection;}

    public void save(Object entity){

    }

}
