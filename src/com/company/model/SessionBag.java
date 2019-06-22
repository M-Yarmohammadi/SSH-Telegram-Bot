package com.company.model;

import com.company.core.SSHConnection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class SessionBag {
    private static SessionBag instance = null;

    private Map<Integer, List<SessionTo>> bag = new Hashtable<>();

    private SessionBag() {
    }

    public static SessionBag getInstance() {
        if (instance == null) {
            instance = new SessionBag();
        }

        return instance;
    }

    public void add(int uid, SessionTo sessionTo) {
        List listOfSessionTos = new ArrayList<>();
        if (bag.containsKey(uid)) {
            listOfSessionTos = bag.get(uid);
        }

        listOfSessionTos.add(sessionTo);
        bag.put(uid, listOfSessionTos);
    }

    public SessionTo retrieveSession(int uid, SessionTo sessionTo) {
        try {
            List<SessionTo> listOfAllUserSessions = bag.get(uid);
            for (SessionTo item : listOfAllUserSessions) {
                if (sessionTo.getServer().getName().equals(item.getServer().getName())) {
                    return item;
                }
            }

        } catch (Exception e) {

        }

        //Ignore me;
        return new SessionTo();
    }

    public boolean hasActiveSession(int uid, SessionTo sessionTo) {
        if (bag.containsKey(uid)) {
            List<SessionTo> listOfAllUserSessions = bag.get(uid);

            for (SessionTo item : listOfAllUserSessions) {
                if (sessionTo.getServer().getName().equals(item.getServer().getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<SessionTo> retrieveAllUserSessions(int uid) {
        if (bag.containsKey(uid)) {
            return bag.get(uid);
        } else {
            return new ArrayList<SessionTo>();
        }
    }

    public void closeSession(int uid, SessionTo sessionTo) {
        if (bag.containsKey(uid)) {
            if (hasActiveSession(uid, sessionTo)) {
                SessionTo session = retrieveSession(uid, sessionTo);

                if (bag.get(uid).size() > 1) {
                    List<SessionTo> list = bag.get(uid);
                    list.remove(session);
                    bag.put(uid, list);
                } else {
                    bag.remove(uid);
                }

                SSHConnection.closeSession(session);
            }
        }
    }

    public void closeAllActiveSession(int uid) {
        if (bag.containsKey(uid)) {
            List<SessionTo> list = bag.get(uid);
            for (SessionTo session : list) {
                SSHConnection.closeSession(session);
            }

            bag.remove(uid);
        }
    }

    public Map<Integer, List<SessionTo>> getBag() {
        return bag;
    }

    public void setBag(Map<Integer, List<SessionTo>> bag) {
        this.bag = bag;
    }
}
