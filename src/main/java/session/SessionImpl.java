package session;
import session.util.ObjectHelper;
import session.util.QueryHelper;

import java.sql.*;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

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
    public List<Object> getList(Class theClass, String key, Object value) {
        String selectQuery = QueryHelper.createQuerySELECT(theClass, key);
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Object> list = new LinkedList<>();

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, value);
            rs = pstm.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numberOfColumns = rsmd.getColumnCount();
            while (rs.next()) {
                Object o = theClass.newInstance();
                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnName = rsmd.getColumnName(i);
                    ObjectHelper.setter(o, columnName, rs.getObject(i));
                }
                list.add(o);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Object> getListAll(Class theClass) {
        String selectQuery = QueryHelper.createQuerySELECTAll(theClass);
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Object> list = new LinkedList<>();

        try {
            pstm = conn.prepareStatement(selectQuery);
            //pstm.setObject(1, value);
            rs = pstm.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int numberOfColumns = rsmd.getColumnCount();
            while (rs.next()) {
                Object o = theClass.newInstance();
                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnName = rsmd.getColumnName(i);
                    ObjectHelper.setter(o, columnName, rs.getObject(i));
                }
                list.add(o);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Object getMatch(Class theClass, Object value) {
        String selectQuery  = QueryHelper.createQuerySELECTMatch(theClass);
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

    public void update(Class theClass, String pk, String parameterToUpdate, Object valueOfPrimaryKey, Object valueOfParameterToUpdate) throws SQLException {
        String updateQuery = QueryHelper.createQueryUPDATE(theClass,pk,parameterToUpdate);
        PreparedStatement statement = conn.prepareStatement(updateQuery);
        statement.setObject(1,valueOfParameterToUpdate);
        statement.setObject(2,valueOfPrimaryKey);
        int rowsAffected = statement.executeUpdate();
        statement.close();
    }
}
