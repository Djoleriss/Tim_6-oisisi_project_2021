package View;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.jdbc.MysqlDataSource;

public class My_Connection {

    private static String servername = "localhost";
    private static String username = "root";
    private static String dbname = "users_db";
    private static int portnumber = 3306;
    private static String password = "admin123";
    static Connection connection = null;
    

    public static void getConnection() {

        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setServerName(servername);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(dbname);
        dataSource.setPort(portnumber);

        try {
        	connection = dataSource.getConnection();
        }catch (SQLException ex){
            Logger.getLogger(" Get Connection -> " + My_Connection.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    public static Connection getInstance() {
        return connection;
    }

}
