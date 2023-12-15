package session;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FactorySession {
    public static Session openSession() {

        Connection conn = getConnection();

        Session session = new SessionImpl(conn);

        return session;
    }

    private static Connection getConnection() {
        Connection conn = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mariadb://localhost:3306/pixelrushdb?" + "user=root&password=1234");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection con = getConnection();
        Statement st = null;

        try {
            st = con.createStatement();
            st.execute("INSERT INTO user (username,password,mail,name,surname,photo,state,birthDate,pointsEarned) " +
                    "VALUES ('robertoguarneros11','123','roberto@gmail.com','Roberto', 'Guarneros',null,null,'12/07/2000',990)");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
