package Model;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class My_Connection {

    private static String servername = "localhost";
    private static String username = "root";
    private static String dbname = "users_db";
    private static int portnumber = 3307;
    private static String password = "";

    public static Connection getConnection() {
        Connection connection = null;

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


        return connection;
    }

}