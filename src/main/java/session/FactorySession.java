package session;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FactorySession {
    public static Session openSession() {
        Connection conn = getConnection();
        return new SessionImpl(conn);
    }

    private static Connection getConnection() {
        Connection conn = null;
        try {
            //conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pixelrushdb?" + "user=root&password=1234");
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/pixelrushdb?" + "user=root&password=Mazinger72");

        } catch (SQLException ex) {
            // handle any errors
            handleSQLException(ex);
        }
        return conn;
    }
    private static void handleSQLException(SQLException ex) {
        // Handle exceptions more gracefully (e.g., log the exception)
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        throw new RuntimeException(ex);
    }
    public static void main(String[] args) {
        Connection con = getConnection();
        Statement st = null;

        try {
            st = con.createStatement();
            st.execute("INSERT INTO user (username, password, mail, name, surname, photo, state, birthDate, pointsEarned) " +
                    "VALUES ('robertoguarneros11', '123', 'roberto@gmail.com', 'Roberto', 'Guarneros', null, null, '2000-07-12', 990)");
        } catch (SQLException e) {
            handleSQLException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }
}
