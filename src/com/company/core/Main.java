package com.company.core;

import com.company.api.core.Bot;
import com.company.api.entity.Message;
import com.company.api.requestObject.RequestGetUpdate;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends TimerTask {

    public static int offset;
    int counter = 1;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Timer timer = new Timer();
        timer.schedule(new Main(), 0, 1000);
        timer.schedule(new Processor(), 0, 1000);
    }

    @Override
    public void run() {
        try {
            List<Message> newMessageList = Bot.getInstance().getUpdates(new RequestGetUpdate(offset));
            printLog();
            for (Message item : newMessageList) {
                Processor.tasksList.add(item);
                offset = item.getUpdateId() + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLog(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println(counter++ + " - " + dateFormat.format(date));
    }
}
