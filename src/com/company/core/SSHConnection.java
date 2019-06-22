package com.company.core;

import com.company.model.SessionTo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class SSHConnection {

    private String user;
    private String password;
    private String host;
    private int port = 22;

    public SSHConnection() {
    }

    public SSHConnection(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public Session createSession() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(this.user, this.host, this.port);
        session.setPassword(this.password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        return session;
    }

    public static void closeSession(SessionTo sessionTo) {
        if (sessionTo.getSession() != null) {
            sessionTo.getSession().disconnect();
        }
    }

    public static String execute(SessionTo sessionTo, String command) throws IOException, JSchException {
        ChannelExec channelExec = (ChannelExec) sessionTo.getSession().openChannel("exec");
        InputStream inputStream = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder stringBuilder = new StringBuilder("");

        System.out.println("before ***");

        while ((line = reader.readLine()) != null) {
            System.out.println("during ***");
            stringBuilder.append(line).append("\n");
        }

        System.out.println("after ***");

        reader.close();
        inputStream.close();

        channelExec.disconnect();
        return stringBuilder.toString();
    }

    public static Boolean exec(SessionTo sessionTo, String command) throws IOException, JSchException {
        try {
            ChannelExec channelExec = (ChannelExec) sessionTo.getSession().openChannel("exec");
            channelExec.setCommand(command);
            channelExec.connect();

            channelExec.disconnect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
