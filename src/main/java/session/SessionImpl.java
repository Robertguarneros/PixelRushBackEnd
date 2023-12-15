package session;
import edu.upc.dsa.models.User;
import session.util.ObjectHelper;
import session.util.QueryHelper;

import java.sql.*;
import java.sql.Connection;

public class SessionImpl implements Session{
    private final Connection conn;

    public SessionImpl(Connection conn){this.conn = conn;}

    public void save(Object entity, String primaryKey){
        String insertQuery = QueryHelper.createQueryINSERT(entity, primaryKey);
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
            int i = 1;
            for (String field : ObjectHelper.getFields(entity)) {
                if (!field.equalsIgnoreCase(primaryKey)) {
                    preparedStatement.setObject(i++, ObjectHelper.getter(entity, field));
                }
            }
            preparedStatement.executeQuery();
        }catch (SQLException e) {
            // Handle the exception more gracefully (log or throw a custom exception)
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

    public Object get(Object entity, String primaryKey, Object value){
        String selectQuery = QueryHelper.createQuerySELECT(entity, primaryKey);
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(selectQuery);
            preparedStatement.setObject(1,value);
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData setMetaData = resultSet.getMetaData();
            int nColunsm = setMetaData.getColumnCount();
            Object o = entity.getClass().newInstance();
            Object valueColumn = null;

            while(resultSet.next()){
                for (int i = 1; i <= nColunsm; i++) {
                    String nameColumn = setMetaData.getColumnName(i);
                    ObjectHelper.setter(o, nameColumn, resultSet.getObject(i));
                    System.out.println((nameColumn));
                    System.out.println((resultSet.getObject(i)));
                    valueColumn = resultSet.getObject(i);
                }
            }
            return o;

        }catch (SQLException e){

            e.printStackTrace();

        }catch (InstantiationException | IllegalAccessException e){

            throw new RuntimeException(e);

        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                // Handle the exception more gracefully (log or throw a custom exception)
                e.printStackTrace();
            }
            return  null;
        }
    }
}
