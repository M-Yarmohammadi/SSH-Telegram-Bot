package com.company.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yarmohammadi on 2/6/2016 AD.
 */
public class Config {
    private String token;
    private String databaseFilePath;
    private String databaseHostName;
    private String databaseUser;
    private String databasePassword;

    private static Config config = null;

    private Config() throws IOException {
        this.load();
    }

    public static Config getInstance() throws IOException {
        if (config == null) {
            config = new Config();
        }

        return config;
    }

    private void load() {
        Properties properties = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream("configuration.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.token = properties.getProperty("bot.token");
        this.databaseFilePath = properties.getProperty("database.file.path");
        this.databaseHostName = properties.getProperty("database.hostname");
        this.databaseUser = properties.getProperty("database.user");
        this.databasePassword = properties.getProperty("database.password");
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDatabaseHostName() {
        return databaseHostName;
    }

    public void setDatabaseHostName(String databaseHostName) {
        this.databaseHostName = databaseHostName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseFilePath() {
        return databaseFilePath;
    }

    public void setDatabaseFilePath(String databaseFilePath) {
        this.databaseFilePath = databaseFilePath;
    }
}
