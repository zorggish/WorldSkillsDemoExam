package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDatabase
{
    MysqlDataSource source = new MysqlDataSource();

    public MysqlDatabase(String address, int port, String database, String user, String password)
    {
        source.setServerName(address);
        source.setPort(port);
        source.setDatabaseName(database);
        source.setUser(user);
        source.setPassword(password);
    }

    public Connection getConnection() throws SQLException
    {
        return source.getConnection();
    }
}
