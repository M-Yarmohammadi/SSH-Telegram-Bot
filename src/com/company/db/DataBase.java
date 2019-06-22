package com.company.db;

import com.company.configuration.Config;

import java.io.IOException;
import java.sql.*;

/**
 * Created by yarmohammadi on 2/6/2016 AD.
 */
public class DataBase {

    public Connection connection = null;
    public PreparedStatement statement = null;
    public ResultSet resultSet = null;

    public void getConnection() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + Config.getInstance().getDatabaseFilePath());
    }

    public void closeConnection() throws SQLException {
        if (this.resultSet != null)
            this.resultSet.close();
        if (this.statement != null)
            this.statement.close();

        this.connection.close();
    }
}
