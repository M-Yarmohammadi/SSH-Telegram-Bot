package com.company.model;

import com.company.db.DataBase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class Authorization {
    private int id;
    private int uid;
    private int serverId;

    public Authorization() {
    }

    public Authorization(int uid, int serverId) {
        this.uid = uid;
        this.serverId = serverId;
    }

    public void findAuthorization() throws SQLException, IOException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.getConnection();
        db.statement = db.connection.prepareStatement("SELECT * FROM authorization WHERE id = ?");
        db.statement.setInt(1, this.id);
        db.resultSet = db.statement.executeQuery();

        while (db.resultSet.next()) {
            this.uid = db.resultSet.getInt("uid");
            this.serverId = db.resultSet.getInt("server_id");
        }

        db.closeConnection();
    }

    public List<Authorization> attachAllAuthorizationForSpecificUser() throws SQLException, IOException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.getConnection();
        db.statement = db.connection.prepareStatement("SELECT * FROM authorization WHERE uid = ?");
        db.statement.setInt(1, this.uid);

        List<Authorization> list = new ArrayList<Authorization>();
        while (db.resultSet.next()) {
            Authorization item = new Authorization();
            item.id = db.resultSet.getInt("id");
            item.uid = db.resultSet.getInt("uid");
            item.serverId = db.resultSet.getInt("server_id");

            list.add(item);
        }

        db.closeConnection();
        return list;
    }

    public boolean isAuthorize() throws SQLException, IOException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.getConnection();
        db.statement = db.connection.prepareStatement("SELECT * FROM authorization WHERE uid = ? AND server_id = ?");
        db.statement.setInt(1, this.uid);
        db.statement.setInt(2, this.serverId);

        db.resultSet = db.statement.executeQuery();
        if (db.resultSet.isBeforeFirst()) {
            db.closeConnection();
            return true;
        }

        db.closeConnection();
        return false;
    }

    public boolean isValidUser() throws SQLException, IOException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.getConnection();
        db.statement = db.connection.prepareStatement("SELECT * FROM authorization WHERE uid = ?");
        db.statement.setInt(1, this.uid);

        db.resultSet = db.statement.executeQuery();
        while (db.resultSet.isBeforeFirst()) {
            db.closeConnection();
            return true;
        }

        db.closeConnection();
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
