package session;
import session.util.ObjectHelper;
import session.util.QueryHelper;

import java.sql.*;
import java.sql.Connection;

public class SessionImpl {
    private final Connection conn;

    public SessionImpl(Connection connnection){this.conn = connnection;}

    public void save(Object entity, String primaryKey) throws SQLException{
        String insertQuery = QueryHelper.createQueryINSERT(entity, primaryKey);
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(insertQuery);
            int i =1;
            for(String field: ObjectHelper.getFields(entity)){
                if (!field.equalsIgnoreCase(primaryKey)){
                    preparedStatement.setObject(i++, ObjectHelper.getter(entity, field));
                }
            }

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void close(){
        try {
            this.conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
