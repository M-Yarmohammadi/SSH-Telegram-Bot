package com.company.model;

import com.jcraft.jsch.Session;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class SessionTo {

    private Session session;
    private Server server;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
