package com.company.core;

import com.company.api.core.Bot;
import com.company.api.entity.Message;
import com.company.api.requestObject.RequestSendMessage;
import com.company.exception.AccessPermissionException;
import com.company.exception.ExecuteCommandException;
import com.company.exception.InvalidCommandException;
import com.company.exception.InvalidSessionException;
import com.company.model.Authorization;
import com.company.model.Server;
import com.company.model.SessionBag;
import com.company.model.SessionTo;
import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by yarmohammadi on 2/8/2016 AD.
 */
public class Processor extends TimerTask {
    public static LinkedList<Message> tasksList = new LinkedList<Message>();

    @Override
    public void run() {
        while (tasksList.size() > 0) {
            try {
                processMessage(tasksList.removeFirst());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSchException e) {
                e.printStackTrace();
            }
        }
    }

    private void processMessage(Message task) throws IOException, SQLException, ClassNotFoundException, JSchException {

        Authorization authorization = new Authorization();
        authorization.setUid(task.getFrom().getId());

        if (task.getText().trim().equalsIgnoreCase("/uid")) {
            uidCommand(task);
        } else if (authorization.isValidUser()) {
            if (task.getText().trim().toLowerCase().startsWith("/startsession")) {
                createSessionCommand(task);
            } else if (task.getText().trim().toLowerCase().startsWith("/execute")) {
                executeCommand(task);
            } else if (task.getText().trim().toLowerCase().startsWith("/exec")) {
                execCommand(task);
            } else if (task.getText().trim().equalsIgnoreCase("/showsessions")) {
                showSessionsCommand(task);
            } else if (task.getText().trim().startsWith("/closesession")) {
                closeSessionCommand(task);
            } else if (task.getText().trim().equalsIgnoreCase("/closeallsession")) {
                closeAllSessionCommand(task);
            } else if (task.getText().trim().equalsIgnoreCase("/help") || task.getText().trim().equalsIgnoreCase("/start")) {
                helpCommand(task);
            } else {
                new InvalidCommandException(task);
            }
        } else {
            RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "**Access denied !**");
            requestSendMessage.setParseMode("Markdown");
            Bot.getInstance().sendMessage(requestSendMessage);
        }
    }

    private void uidCommand(Message task) throws IOException {
        RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "Your uid is : " + task.getFrom().getId());
        requestSendMessage.setReplyToMessageId(task.getMessageId());

        Bot.getInstance().sendMessage(requestSendMessage);
    }

    private void createSessionCommand(Message task) throws IOException, JSchException, SQLException, ClassNotFoundException {
        String message = task.getText().trim();
        String[] values = message.split("\\s+");
        if (values.length < 4) {            //SERVER NAME or USERNAME or PASSWORD NOT EXIST;
            new InvalidCommandException(task);
        } else {
            // Check session not exist;
            Server server = new Server();
            server.setName(values[1]);
            server.findServerByName();

            SessionTo sessionTo = new SessionTo();
            sessionTo.setServer(server);

            if (SessionBag.getInstance().hasActiveSession(task.getFrom().getId(), sessionTo)) {
                RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "Session on " + sessionTo.getServer().getName() + " server has already active.");
                requestSendMessage.setReplyToMessageId(task.getMessageId());
                Bot.getInstance().sendMessage(requestSendMessage);
            } else {
                Authorization authorization = new Authorization(task.getFrom().getId(), server.getId());

                if (authorization.isAuthorize()) {           //user has access permission to server;
                    SSHConnection ssh = new SSHConnection(server.getIp(), values[2], values[3]);
                    try {
                        sessionTo.setSession(ssh.createSession());
                    } catch (JSchException e) {
                        RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), e.getMessage());
                        requestSendMessage.setReplyToMessageId(task.getMessageId());
                        Bot.getInstance().sendMessage(requestSendMessage);
                        return;
                    }
                    SessionBag.getInstance().add(task.getFrom().getId(), sessionTo);

                    RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "Session on " + sessionTo.getServer().getName() + " server created successful.");
                    requestSendMessage.setReplyToMessageId(task.getMessageId());
                    Bot.getInstance().sendMessage(requestSendMessage);
                } else {                                     //user has not access permission to server;
                    new AccessPermissionException(task);
                }
            }
        }
    }

    private void executeCommand(Message task) throws SQLException, IOException, ClassNotFoundException, JSchException {
        String message = task.getText().trim();
        String[] values = message.split("\\s+");

        if (values.length < 3) {            // command parameter is incorrect;
            new InvalidCommandException(task);
        } else {
            Server server = new Server();
            server.setName(values[1]);
            server.findServerByName();

            SessionTo sessionTo = new SessionTo();
            sessionTo.setServer(server);

            if (SessionBag.getInstance().hasActiveSession(task.getFrom().getId(), sessionTo)) {
                StringBuilder command = new StringBuilder(values[2]);

                for (int counter = 3; counter < values.length; counter++) {
                    command.append(" ").append(values[counter]);
                }

                String response = SSHConnection.execute(SessionBag.getInstance().retrieveSession(task.getFrom().getId(),
                        sessionTo), command.toString());

                if (response.equals("")) {
                    response = "Command execute successful.";
                }

                RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), response);
                requestSendMessage.setReplyToMessageId(task.getMessageId());
                Bot.getInstance().sendMessage(requestSendMessage);
            } else {
                new InvalidSessionException(task);
            }

        }
    }

    public void execCommand(Message task) throws IOException, SQLException, ClassNotFoundException, JSchException {
        String message = task.getText().trim();
        String[] values = message.split("\\s+");

        if (values.length < 3) {            // command parameter is incorrect;
            new InvalidCommandException(task);
        } else {
            Server server = new Server();
            server.setName(values[1]);
            server.findServerByName();

            SessionTo sessionTo = new SessionTo();
            sessionTo.setServer(server);

            if (SessionBag.getInstance().hasActiveSession(task.getFrom().getId(), sessionTo)) {
                StringBuilder command = new StringBuilder(values[2]);

                for (int counter = 3; counter < values.length; counter++) {
                    command.append(" ").append(values[counter]);
                }

                boolean response = SSHConnection.exec(SessionBag.getInstance().retrieveSession(task.getFrom().getId(),
                        sessionTo), command.toString());

                if (response) {
                    RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "Command execute successful.");
                    requestSendMessage.setReplyToMessageId(task.getMessageId());
                    Bot.getInstance().sendMessage(requestSendMessage);
                } else {
                    new ExecuteCommandException(task);
                }


            } else {
                new InvalidSessionException(task);
            }

        }
    }

    private void showSessionsCommand(Message task) throws IOException {
        List<SessionTo> listOfAllSession = SessionBag.getInstance().retrieveAllUserSessions(task.getFrom().getId());

        if (listOfAllSession.isEmpty()) {
            RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "No any active session.use /startSession to create new one.");
            requestSendMessage.setReplyToMessageId(task.getMessageId());
            Bot.getInstance().sendMessage(requestSendMessage);
        } else {

            StringBuilder response = new StringBuilder();
            for (SessionTo item : listOfAllSession) {
                response.append(item.getServer().getName()).append("\n");
            }

            RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), response.toString());
            requestSendMessage.setReplyToMessageId(task.getMessageId());
            Bot.getInstance().sendMessage(requestSendMessage);
        }
    }

    private void closeSessionCommand(Message task) throws IOException, SQLException, ClassNotFoundException {
        String message = task.getText().trim();
        String[] values = message.split("\\s+");
        if (values.length < 2) {            //SERVER NAME or USERNAME or PASSWORD NOT EXIST;
            new InvalidCommandException(task);
        } else {
            Server server = new Server();
            server.setName(values[1]);
            server.findServerByName();

            SessionTo sessionTo = new SessionTo();
            sessionTo.setServer(server);
            SessionBag.getInstance().closeSession(task.getFrom().getId(), sessionTo);

            RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "Closing session successful.");
            requestSendMessage.setReplyToMessageId(task.getMessageId());
            Bot.getInstance().sendMessage(requestSendMessage);
        }
    }

    private void closeAllSessionCommand(Message task) throws IOException {
        SessionBag.getInstance().closeAllActiveSession(task.getFrom().getId());

        RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), "All your active session closed.");
        requestSendMessage.setReplyToMessageId(task.getMessageId());
        Bot.getInstance().sendMessage(requestSendMessage);
    }

    public void helpCommand(Message task) throws IOException {
        StringBuilder helpMessage = new StringBuilder();

        helpMessage.append("/help").append(" -> ").append("Show help (this menu).").append("\n");
        helpMessage.append("/startSession [server_name] [username] [password]").append(" -> ").append("Start session on server.").append("\n");
        helpMessage.append("/execute [server_name] [command]").append(" -> ").append("Execute command on server with output.").append("\n");
        helpMessage.append("/exec [server_name] [command]").append(" -> ").append("Execute command on server without output.").append("\n");
        helpMessage.append("/showSessions").append(" -> ").append("Show all active sessions.").append("\n");
        helpMessage.append("/closeSession [server_name]").append(" -> ").append("Close specific session.").append("\n");
        helpMessage.append("/closeAllSession").append(" -> ").append("Close all active sessions.").append("\n");
        helpMessage.append("/uid").append(" -> ").append("Show your uid").append("\n");

        RequestSendMessage requestSendMessage = new RequestSendMessage(task.getChat(), helpMessage.toString());
        requestSendMessage.setReplyToMessageId(task.getMessageId());
        Bot.getInstance().sendMessage(requestSendMessage);
    }
}
