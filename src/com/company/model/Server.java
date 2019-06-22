package com.company.model;

import com.company.db.DataBase;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class Server {
    private int id;
    private String name;
    private String ip;

    public void findServer() throws SQLException, IOException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.getConnection();
        db.statement = db.connection.prepareStatement("SELECT * FROM server where id = ?");
        db.statement.setInt(1, this.id);
        db.resultSet = db.statement.executeQuery();

        while (db.resultSet.next()){
            this.name = db.resultSet.getString("name");
            this.ip = db.resultSet.getString("ip");
        }

        db.closeConnection();
    }

    public void findServerByName() throws SQLException, IOException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.getConnection();
        db.statement = db.connection.prepareStatement("SELECT * FROM server where name = ?");
        db.statement.setString(1, this.name);
        db.resultSet = db.statement.executeQuery();

        while (db.resultSet.next()){
            this.id = db.resultSet.getInt("id");
            this.ip = db.resultSet.getString("ip");
        }

        db.closeConnection();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
