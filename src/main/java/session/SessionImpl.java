package session;
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

    /*public Object get(Object entity, String primaryKey, Object value){
        String selectQuery = QueryHelper.createQuerySELECT(entity, primaryKey);
        ResultSet resultSet=null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(selectQuery);
            preparedStatement.setObject(1,value);
            resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int numberOfColumns = resultSetMetaData.getColumnCount();
            Object o = entity.getClass().newInstance();
            Object valueColumn = null;

            while(resultSet.next()){
                for (int i = 1; i <= numberOfColumns; i++) {
                    String nameColumn = resultSetMetaData.getColumnName(i);
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
    }*/
    public Object get(Class theClass, String pk, Object value) {
        String selectQuery  = QueryHelper.createQuerySELECT(theClass, pk);
        ResultSet rs;
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, value); //son los ?
            rs = pstm.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            //
            int numberOfColumns = rsmd.getColumnCount();
            //
            Object o = theClass.newInstance();
            //
            Object valueColumn = null;
            while (rs.next()){
                for (int i=1; i<=numberOfColumns; i++){
                    String columnName = rsmd.getColumnName(i);
                    ObjectHelper.setter(o, columnName, rs.getObject(i));
                    System.out.println(columnName);
                    System.out.println(rs.getObject(i));
                    valueColumn = rs.getObject(i);
                    //if (valueColumn!=null) ObjectHelper.setter(o, columnName, rs.getObject(i));
                }
            }
            return o;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
